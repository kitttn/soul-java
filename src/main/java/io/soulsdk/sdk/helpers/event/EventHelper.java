package io.soulsdk.sdk.helpers.event;

import java.util.List;

import io.soulsdk.model.dto.Meta;
import io.soulsdk.model.dto.event.Event;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import log.Log;
import rx.Observable;
import rx.Statement;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * @author kitttn
 */

public class EventHelper {
    private static final String TAG = "EventHelper";
    private PublishSubject<List<Event>> eventStream = PublishSubject.create();
    private ServerAPIHelper helper;
    private boolean isRefreshing = false;
    private boolean loadMore = false;

    public EventHelper(ServerAPIHelper helper) {
        this.helper = helper;
    }

    public Observable<List<Event>> getLatest(double requestSince, int limit) {
        PublishSubject<List<Event>> stream = getSubject();

        Log.i(TAG, "getLatest: Subscribing to: " + stream);
        helper.getEvents(requestSince, limit, false, null)
                .subscribeOn(Schedulers.io())
                .filter(soul -> notifySubjectIfError(stream, soul))
                .map(SoulResponse::getResponse)
                .map(resp -> {
                    Meta meta = resp.get_meta();
                    System.out.println("Meta: " + meta);
                    isRefreshing = false;

                    if (meta.getTotal() < meta.getLimit())
                        loadMore = false;

                    return resp.getEvents();
                })
                .subscribe(stream);

        return stream;
    }

    public Observable<List<Event>> getAllLatest(double requestSince) {
        loadMore = true;

        Observable<List<Event>> loader = Observable.just(true)
                .flatMap(b -> getLatest(requestSince, 100));

        return Statement.whileDo(loader, () -> loadMore);
    }

    private PublishSubject<List<Event>> getSubject() {
        if (eventStream.hasCompleted() || eventStream.hasThrowable())
            eventStream = PublishSubject.create();

        Log.i(TAG, "getSubject: You're subscribing on subId=" + eventStream);
        return eventStream;
    }

    private <T, R> boolean notifySubjectIfError(PublishSubject<R> sub, SoulResponse<T> response) {
        if (response.isErrorHappened())
            sub.onError(response.getError());

        return !response.isErrorHappened();
    }
}

package io.soulsdk.sdk;

import java.util.List;

import io.soulsdk.model.dto.event.Event;
import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import io.soulsdk.sdk.helpers.event.EventHelper;
import rx.Observable;

/**
 * <p>
 * Provides a list of methods for working with Events.
 * </p>
 *
 * @author Buiarov Uirii
 * @version 0.20
 * @since 28/03/16
 */
public class SoulEvents {
    private EventHelper helper;

    public SoulEvents(ServerAPIHelper hp) {
        helper = new EventHelper(hp);
    }

    /**
     * Returns latest events sorted by id. If you're calling this method at the first time, you will
     * get all results similar to getAll(). Every next call you will retrieve only missed events.
     * This call returns a number of lists, depends on the number of events you have on server
     * (max 10 lists x 100 Events)
     * <p>
     * <p>Please, note, that this is a cold finite Observable. It will not yield data eventually, and you
     * need to resubscribe every time you wish to get updates.</p>
     *
     * @return observable of {@link Event}s with data; All errors are emitted via onError
     */
    public Observable<List<Event>> getLatest(double requestSince) {
        return helper.getAllLatest(requestSince);
    }

    /**
     * Returns latest events sorted by id. If you're calling this method at the first time, you will
     * get all results similar to getAll(). Every next call you will retrieve only missed events.
     *
     * @param callback this is the callback you will receive data into. It will be notified on every
     *                 {@link Event} you missed.
     */
    public void getLatest(SoulCallback<Event> callback) {
    }
}
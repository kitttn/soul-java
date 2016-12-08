package io.soulsdk.network.executor;

import java.util.concurrent.TimeUnit;

import io.soulsdk.util.HttpUtils;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by buyaroff1 on 01/12/15.
 */
public class RetryOnConnectionErrorObservable implements Func1<Observable<? extends Throwable>, Observable<?>> {

    public static final int ATTEMPTS_MAX_COUNT = 3;
    public static final int ATTEMPTS_AUTH_MAX_COUNT = 1;

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts
                .filter(new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return HttpUtils.isConnectionError(throwable)
                                || HttpUtils.isLimitPerSecondError(throwable);
                    }
                })
                .zipWith(Observable.range(1, ATTEMPTS_MAX_COUNT),
                        (throwable, integer) -> integer


                /*        Observable.range(1, ATTEMPTS_MAX_COUNT), (n, i) -> {
                    return i;
                }*/)
                .flatMap((Func1<Integer, Observable<?>>) i -> Observable.timer(i, TimeUnit.SECONDS)
                        /*new Func1<Integer, Observable<? extends Long>>() {
                    @Override
                    public Observable<? extends Long> call(Integer i) {
                        return Observable.timer(i, TimeUnit.SECONDS);
                    }
                }*/);
    }


/*    return attempts
            .filter(throwable -> HttpUtils.isConnectionError(throwable)
            || HttpUtils.isLimitPerSecondError(throwable))
            .zipWith(Observable.range(1, ATTEMPTS_MAX_COUNT), (n, i) -> i)
            .flatMap(i -> Observable.timer(i, TimeUnit.SECONDS));*/

}

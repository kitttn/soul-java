package io.soulsdk.network.executor;

import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulError;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.responses.AuthorizationResponse;
import io.soulsdk.network.SoulService;
import io.soulsdk.network.managers.Credentials;
import io.soulsdk.sdk.SoulAuth;
import io.soulsdk.util.HttpUtils;
import log.Log;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Buiarov on 04/03/16.
 */
public class RequestExecutor<T> {

    private static final String TAG = "RequestExecutor";
    public SoulService soulService;
    public Subscriber<? super SoulResponse<T>> mainSubscriber;
    public Subscriber<? super SoulResponse<T>> finalSubscriber;
    String methodName = "n/a";
    private int ATTEMPTS_COUNT = 0;
    private int ATTEMPTS_AUTH_COUNT = 0;
    private SoulAuth auth;
    private ResponseParser<T> responseParser;
    private SoulCallback soulCallback;
    private Observable<Response<T>> responseObservable;
    private boolean isSearch = false;
    private boolean isSuccess = false;

    public RequestExecutor(SoulAuth auth, Observable<Response<T>> responseObservable, Credentials credentials) {
        this.responseObservable = responseObservable;
        this.soulCallback = null;
        responseParser = new ResponseParser<>(credentials, null, isSearch);
        this.auth = auth;
    }

    public RequestExecutor(SoulAuth auth, Observable<Response<T>> responseObservable, Credentials credentials, SoulService soulService) {
        this.responseObservable = responseObservable;
        this.soulService = soulService;
        this.soulCallback = null;
        responseParser = new ResponseParser(credentials, null, isSearch);
        this.auth = auth;
    }

    public RequestExecutor(SoulAuth auth, Observable<Response<T>> responseObservable, Credentials credentials,
                           SoulCallback soulCallback, boolean isSearch) {
        this.responseObservable = responseObservable;
        this.isSearch = isSearch;
        this.soulCallback = soulCallback;
        responseParser = new ResponseParser(credentials, null, isSearch);
        this.auth = auth;
    }

    public RequestExecutor(SoulAuth auth, String methodName, Observable<Response<T>> responseObservable, Credentials credentials,
                           SoulCallback soulCallback, boolean isSearch) {
        this.methodName = methodName;
        this.responseObservable = responseObservable;
        this.isSearch = isSearch;
        this.soulCallback = soulCallback;
        responseParser = new ResponseParser(credentials, null, isSearch);
        this.auth = auth;
    }

    public RequestExecutor(SoulAuth auth, Observable<Response<T>> responseObservable,
                           Credentials credentials, SoulCallback soulCallback,
                           String authSource) {
        this.responseObservable = responseObservable;
        this.soulCallback = soulCallback;
        responseParser = new ResponseParser(credentials, authSource, isSearch);
        this.auth = auth;
    }

    public RequestExecutor(SoulAuth auth, Observable<Response<T>> responseObservable,
                           Credentials credentials, SoulCallback soulCallback,
                           String authSource, String tempLogin, String tempPass) {
        this.responseObservable = responseObservable;
        this.soulCallback = soulCallback;
        responseParser = new ResponseParser(credentials, authSource, isSearch, tempLogin, tempPass);
        this.auth = auth;
        credentials.saveTempLoginPass(tempLogin, tempPass);
    }

    public RequestExecutor(SoulAuth auth, Observable<Response<T>> responseObservable, Credentials credentials,
                           SoulCallback soulCallback, boolean isSearch, boolean isLogout) {
        this.responseObservable = responseObservable;
        this.isSearch = isSearch;
        this.soulCallback = soulCallback;
        this.auth = auth;
    }

    public Observable<SoulResponse<T>> asObservable() {
        if (soulCallback != null) {
            makeRequest(responseObservable, null);
            return null;
        } else {
            return Observable.create(new Observable.OnSubscribe<SoulResponse<T>>() {
                @Override
                public void call(Subscriber<? super SoulResponse<T>> subscriber) {
                    RequestExecutor.this.finalSubscriber = subscriber;
                    execute();
                }
            });
        }
    }


    private void execute() {
        //Log.d("RELOGIN", RequestExecutor.this.hashCode() + " : " + methodName + " execute ");
        RequestExecutor.this.makeRequest(RequestExecutor.this.responseObservable,
                RequestExecutor.this.finalSubscriber);
    }

    private void makeRequest(Observable<Response<T>> responseObservable,
                             Subscriber<? super SoulResponse<T>> subscriber) {
        this.mainSubscriber = subscriber;
        doRequest(responseObservable)
                .retryWhen(new RetryOnConnectionErrorObservable())
                .subscribe(
                        res ->
                                finalSuccess(res),
                        (err)
                                -> errorWrapper(err),
                        () ->
                        {
                            // Log.d("RELOGIN", RequestExecutor.this.hashCode() + " : " + methodName + " makeRequest ");
                            finalComplete();
                        }
                );
    }

    private void errorWrapper(Throwable err) {
        err.printStackTrace();
        finalFail(HttpUtils.createSoulError(err, false));
    }

    private void finalSuccess(Response<T> res) {
        isSuccess = res.isSuccessful();
        responseParser.parseResult(res.body());
        checkBooleanResult(res.body(), true);
        if (!checkUnauthorized(res)) {
            if (soulCallback != null) soulCallback.onSuccess(res);
            if (mainSubscriber != null)
                notifyMainsubscriber(res);
        } else {
            // Log.d(TAG, "Relogin is required");
            // Log.d("RELOGIN", RequestExecutor.this.hashCode() + " : " + methodName + " Relogin is required ");
            if (ATTEMPTS_AUTH_COUNT > 0) notifyMainsubscriber(res);
            else reLogin(res);
        }
    }

    private void notifyMainsubscriber(Response<T> res) {
        // Log.d("RELOGIN", RequestExecutor.this.hashCode() + " : " + methodName + " mainSubscriber.onNext(new SoulResponse<T>(res, sentryCatcher)); ");
        mainSubscriber.onNext(new SoulResponse<T>(res));
    }

    private void finalFail(SoulError err) {
        checkBooleanResult(null, false);
        if (soulCallback != null) soulCallback.onError(err);
        if (mainSubscriber != null) mainSubscriber.onNext(new SoulResponse<T>(err));
    }

    private void finalComplete() {
        if (isSuccess) {
            // Log.d("RELOGIN", RequestExecutor.this.hashCode() + " : " + methodName + " finalComplete ");
            if (mainSubscriber != null) mainSubscriber.onCompleted();
        } else {
            // Log.d("RELOGIN", RequestExecutor.this.hashCode() + " : " + methodName + " finalComplete not success");
        }
    }

    private Observable<Response<T>> doRequest(final Observable<Response<T>> responseObservable) {
        return Observable.create(sub -> responseObservable
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        res ->
                                finalSuccess(res),
                        e ->
                                responseFail(e, sub),
                        () ->
                        {
                            //Log.d("RELOGIN", RequestExecutor.this.hashCode() + " : " + methodName + " doRequest ");
                            finalComplete();
                        }
                ));

    }

    private void responseFail(Throwable err, Subscriber<? super Response<T>> sub) {
        if (HttpUtils.isConnectionError(err) || HttpUtils.isLimitPerSecondError(err)) {
            onConnectionErrorProcess(err, sub);
        } else {
            finalFail(HttpUtils.createSoulError(err, false));
        }
    }

    private void onConnectionErrorProcess(Throwable err, Subscriber<? super Response<T>> sub) {
        ATTEMPTS_COUNT++;
        if (ATTEMPTS_COUNT > RetryOnConnectionErrorObservable.ATTEMPTS_MAX_COUNT)
            finalFail(HttpUtils.createSoulError(err, true));
        else
            sub.onError(err);
    }

    private T checkBooleanResult(T res, boolean value) {
        if (res == null || res instanceof Boolean) {
            return (T) new Boolean(value);
        } else return res;
    }

    private boolean checkUnauthorized(Response<T> res) {
        boolean success = res.isSuccessful();
        boolean code = res.code() == 401;
        return (!success && code);
    }

    private void reLogin(Response<T> failedResponse) {
        ATTEMPTS_AUTH_COUNT++;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        auth.login().subscribe(
                                result -> retryRequestIfSuccess(result, failedResponse),
                                err -> notifyMainsubscriber(failedResponse)
                        );
                    }
                },
                1500
        );
    }

    private void retryRequestIfSuccess(SoulResponse<AuthorizationResponse> response, Response<T> failedResponse) {
        if (response.isErrorHappened()) notifyMainsubscriber(failedResponse);
        else execute();
    }

}
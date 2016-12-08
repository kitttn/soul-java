package io.soulsdk.util;


import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.soulsdk.model.general.SoulError;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by buyaroff1 on 22/02/16.
 */
public class HttpUtils {

    public static SoulError createSoulError(Throwable err, boolean isConnection) {
        if (isConnection)
            return new SoulError("Connection Error", SoulError.NETWORK_ERROR);
        else if (err instanceof HttpException)
            return new SoulError(((HttpException) err).message(), ((HttpException) err).code());
        else {
            err.printStackTrace();
            return new SoulError("Unknown Error", SoulError.UNKNOWN_ERROR);}
    }

    public static boolean isConnectionError(Throwable e) {
        return (e instanceof UnknownHostException ||
                e instanceof ConnectException ||
                e instanceof InterruptedIOException
        );
    }

    public static boolean isLimitPerSecondError(Throwable e) {
        return (e instanceof HttpException &&
                ((HttpException) e).code() == 429);
    }

    public static boolean isAuthError(Throwable e) {
        return (e instanceof HttpException &&
                ((HttpException) e).code() == 401);
    }
}

package io.soulsdk.model.general;

import log.Log;

import io.soulsdk.util.error.ErrorInterceptor;
import retrofit2.Response;

/**
 * General Soul Response
 *
 * @author kitttn
 * @version 0.15
 * @since 28/03/16
 */
public class SoulResponse<T> {
    private static final String TAG = "SoulResponse";
    private static ErrorInterceptor interceptor;
    private SoulError error;
    private boolean hasError = false;
    private T response;
    private int httpStatus;

    public SoulResponse(Response<T> response) {
        this.httpStatus = response.code();
        this.response = response.body();
        if (!response.isSuccessful())
            try {
                hasError = true;
                error = new SoulError(response);
                error.setHttpCode(response.code());
                logServerError(error);

            } catch (Exception e) {
                Log.e(TAG, "SoulResponse: Got error without error message!");
            }
    }

    public SoulResponse(T response) {
        this.response = response;
    }

    public SoulResponse(SoulError error) {
        this.error = error;
        this.hasError = true;
    }

    /**
     * Used to indicate that last request completed successfully. It doesn't return a value
     * neither an error.
     */
    public SoulResponse() {
    }

    public static void setInterceptor(ErrorInterceptor interceptor) {
        SoulResponse.interceptor = interceptor;
    }

    public SoulError getError() {
        return error;
    }

    public boolean isErrorHappened() {
        return hasError;
    }

    public T getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "{error: " + error + "; response: " + response + "}";
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    private void logServerError(SoulError error) {
        int code = error.getHttpCode();
        if (code > 499 && interceptor != null) {
            interceptor.captureError(
                    error.getDescription(),
                    String.valueOf(error.getHttpCode()),
                    "",
                    "error",
                    error.getMethod(),
                    error.getRequestURL(),
                    error.getRequestURL(),
                    error.getRequestParameters(),
                    error.getResponseBody()
            );
        }
    }
}

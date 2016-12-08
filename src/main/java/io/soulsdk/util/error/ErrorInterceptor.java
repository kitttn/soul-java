package io.soulsdk.util.error;

/**
 * @author kitttn
 */

public interface ErrorInterceptor {
    void captureError(String errorText,
                                      String errorCode,
                                      String requestId,
                                      String level,
                                      String method,
                                      String requestURL,
                                      String responseURL,
                                      String parameters,
                                      String responseBody);
}

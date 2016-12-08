package io.soulsdk.model.general;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Response;

/**
 * General Soul Error
 *
 * @author kitttn
 * @version 0.15
 * @since 28/03/16
 */
public class SoulError extends Throwable {

    public static final int UNKNOWN_ERROR = 0;
    public static final int NETWORK_ERROR = 1;

    public static final int PHONE_WRONG_CODE = 2;
    public static final int PHONE_WRONG_NUMBER = 3;
    public static final int PHONE_AUTHENTICATION_ERROR = 4;
    public static final int PHONE_NO_AUTH_CREDENTIALS = 5;

    public static final int PHOTOS_NO_SUCH_ALBUM = 5;
    public static final int PHOTOS_LOADING_ERROR = 16;

    public static final int ALBUMS_CANT_CREATE = 17;

    public static final int LOCATION_NO_PERNISSIONS = 6;
    public static final int LOCATION_NOT_ENOUGH_SOURCES = 7;
    public static final int LOCATION_CANT_SEND = 18;                    // max = 18

    public static final int APP_PAYMENT_REQUIRED = 8;

    public static final int NO_LOGIN_CREDENTIALS = 9;

    public static final int ON_MAIN_THREAD_EXCEPTION = 10;
    public static final int SDK_IS_NOT_INITIALIZED = 11;

    public static final int EVENT_LOADING_ERROR = 12;

    public static final int CHATS_ERROR_LOADING_HISTORY = 13;
    public static final int CHATS_ERROR_LOADING = 15;

    public static final int USERS_LOADING_CACHING_ERROR = 14;
    public static final int USERS_BANNED = 4030;

    public static final int NON_SDK_ERROR = 0xffff;

    @SerializedName("alias")
    private String description = "";
    private String requestParameters = "";
    private String method = "";
    private String requestURL = "";
    private String responseBody = "";
    private String userMessage = "";
    private int code = 0;
    private int httpCode = 0;

    public SoulError(String description, int code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(String requestParameters) {
        this.requestParameters = requestParameters;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public SoulError(Response response) {

        try {
            String jsonError = response.errorBody().string();
            jsonError = jsonError.substring(9, jsonError.length() - 1);
            SoulError error = new Gson().fromJson(jsonError, SoulError.class);
            this.description = error.description;
            this.responseBody = jsonError;
            this.code = error.code;
            this.userMessage = error.userMessage;
            this.httpCode = error.httpCode;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String parameters = bodyToString(response.raw().request().body());
            this.requestParameters = parameters;
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String method = response.raw().request().method();
            this.method = method;
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String requestURL = response.raw().request().url().url().toString();
            this.requestURL = requestURL;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override public String getMessage() {
        return userMessage;
    }

    @Override public String getLocalizedMessage() {
        return userMessage;
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "doesn't work";
        }
    }

}

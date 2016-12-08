package io.soulsdk.network.adapter;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.soulsdk.configs.Config;
import io.soulsdk.model.dto.event.Event;
import io.soulsdk.model.dto.event.EventTypeAdapter;
import io.soulsdk.network.SoulService;
import io.soulsdk.sdk.SoulConfigs;
import io.soulsdk.util.signature.AuthGen;
import log.Log;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by buyaroff1 on 18/01/16.
 */
public class RetrofitAdapter implements NetworkAdapter {

    private static final int CONNECT_TIMEOUT_MILLIS = 60 * 1000;
    private static final int READ_TIMEOUT_MILLIS = 60 * 1000;
    private static final int WRITE_TIMEOUT_MILLIS = 360 * 1000;

    private String USER_AGENT;
    private String requestBody;
    private String userId;

    private String serverHost;

    public RetrofitAdapter(String serverHost) {
        this.serverHost = serverHost;
        refreshUserAgent();
    }

    public Retrofit getSecuredNetworkAdapter(String userId, String requestBody, String sessionToken) {
        this.userId = userId;
        this.requestBody = requestBody;

        return new Retrofit.Builder()
                .baseUrl(this.serverHost)
                .addConverterFactory(getGsonFactory())
                .client(getSecuredClient(sessionToken))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public Retrofit getNetworkAdapter() {
        return new Retrofit.Builder()
                .baseUrl(this.serverHost)
                .addConverterFactory(getGsonFactory())
                .client(getUnsecuredClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Override
    public void refreshUserAgent() {
        this.USER_AGENT = createUserAgent();
    }

    private OkHttpClient getSecuredClient(String sessionToken) {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .addInterceptor(getAuthInterceptor(true, sessionToken))
                .addInterceptor(getLoggingInterceptor())
                .build();
    }

    private OkHttpClient getUnsecuredClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .addInterceptor(getAuthInterceptor(false, ""))
                .addInterceptor(getLoggingInterceptor())
                .build();
    }

    private GsonConverterFactory getGsonFactory() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Event.class, new EventTypeAdapter());
        Gson gson = builder.create();
        return GsonConverterFactory.create(gson);
    }

    private Interceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(msg -> {});
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private Interceptor getAuthInterceptor(final boolean secured, String sessionToken) {
        return chain -> {
            Request original = chain.request();
            String uri = original.url().toString().replaceAll(serverHost + "/", "");
            Request.Builder requestBuilder = (secured) ?
                    original.newBuilder()
                            .header("user-agent", USER_AGENT)
                            .header("Authorization", AuthGen.getBasic(
                                    userId,
                                    sessionToken,
                                    original.method(),
                                    uri,
                                    requestBody))
                            .header("content-type", "application/json")
                            .header("cache-control", "no-cache")
                            .method(original.method(), original.body())
                    :
                    original.newBuilder()
                            .header("user-agent", USER_AGENT)
                            .header("content-type", "application/json")
                            .header("cache-control", "no-cache")
                            .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    private String createUserAgent() {
        String CLIENT_APP_NAME = SoulConfigs.getUserAgentAppName();
        String APP_VERSION = SoulConfigs.getUserAgentAppVersion();

        return ""
                + CLIENT_APP_NAME
                + "/"
                + APP_VERSION
                + " "
                + "(Android 0.0; Dell XPS 13; "
                + Locale.getDefault().toString()
                + "; "
                + "0.9.0.31) "
                + Config.PLATFORM
                + "/" + APP_VERSION
                + " (Android)";
    }


    public SoulService getService() {
        return getNetworkAdapter().create(SoulService.class);
    }

    public SoulService getSecuredService(String userId, String requestBody, String sessionToken) {
        return getSecuredNetworkAdapter(userId, requestBody, sessionToken).create(SoulService.class);
    }
}

package io.soulsdk.network.adapter;

import io.soulsdk.network.SoulService;
import retrofit2.Retrofit;

/**
 * Created by buyaroff1 on 18/02/16.
 */
public interface NetworkAdapter {

    Retrofit getSecuredNetworkAdapter(String userId, String requestBody, String sessionToken);

    Retrofit getNetworkAdapter();

    void refreshUserAgent();

    SoulService getService();

    SoulService getSecuredService(String userId, String requestBody, String sessionToken);
}

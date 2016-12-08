package io.soulsdk.network.managers;

import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.requests.PhoneRequestREQ;
import io.soulsdk.model.requests.PhoneVerifyLoginREQ;

/**
 * Created by Buiarov on 08/03/16.
 */
public interface Credentials {

    SoulResponse<PhoneRequestREQ> getPhoneRequestREQ(String phoneNumber, String method);

    SoulResponse<PhoneVerifyLoginREQ> getPhoneVerifyREQ(String verificationCode, String verificationMethod);

    SoulResponse<PhoneVerifyLoginREQ> getPhoneVerifyREQ();

    void saveSessionToken(String sessionToken);

    void saveTempLoginPass(String login, String pass);

    void clearCredentials();

    String getUserId();

    String getSessionToken();

    void saveUserId(String userId);

    String getLastSuccessfulLoginSource();

    void saveLastSuccessfulLoginSource(String authSource);

    String getTempLogin();

    String getTempPass();
}

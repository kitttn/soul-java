package io.soulsdk.model.requests;

import io.soulsdk.model.general.GeneralRequest;

/**
 * HTTP-request body object for phone verifying
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class PhoneVerifyLoginREQ extends GeneralRequest {

    private String apiKey;
    private String phoneNumber;
    private String code;
    private String lastSessionToken;
    private String method;

    public PhoneVerifyLoginREQ() {
    }

    // Verify phone
    public PhoneVerifyLoginREQ(String apiKey, String code, String phoneNumber, String method) {
        this.apiKey = apiKey;
        this.code = code;
        this.setMethod(method);
        this.phoneNumber = phoneNumber;
    }

    // Login via phone
    public PhoneVerifyLoginREQ(String apiKey, String code, String phoneNumber,  String method, String lastSessionToken) {
        this.apiKey = apiKey;
        this.phoneNumber = phoneNumber;
        this.setMethod(method);
        this.code = code;
        this.lastSessionToken = lastSessionToken;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastSessionToken() {
        return lastSessionToken;
    }

    public void setLastSessionToken(String lastSessionToken) {
        this.lastSessionToken = lastSessionToken;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

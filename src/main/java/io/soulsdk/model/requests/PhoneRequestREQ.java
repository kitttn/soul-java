package io.soulsdk.model.requests;

import io.soulsdk.model.general.GeneralRequest;

/**
 * HTTP-request body object for phone authorization requesting
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class PhoneRequestREQ extends GeneralRequest {

    private String apiKey;
    private String method;
    private String phoneNumber;

    public PhoneRequestREQ() {
    }

    public PhoneRequestREQ(String apiKey, String method, String phoneNumber) {
        this.apiKey = apiKey;
        this.setMethod(method);
        this.phoneNumber = phoneNumber;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

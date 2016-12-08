package io.soulsdk.model.requests;

import io.soulsdk.model.general.GeneralRequest;

/**
 * HTTP-request body object for adding receipt
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class AddReceiptREQ extends GeneralRequest {

    private String token;
    private String signature;
    private String subscriptionId;

    public AddReceiptREQ(String token, String signature, String subscriptionId) {
        this.token = token;
        this.signature = signature;
        this.subscriptionId = subscriptionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}

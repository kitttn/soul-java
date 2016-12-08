package io.soulsdk.model.dto;

/**
 * Membership entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Membership {

    private String subscriptionId;
    private double expiresTime;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public double getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(double expiresTime) {
        this.expiresTime = expiresTime;
    }
}

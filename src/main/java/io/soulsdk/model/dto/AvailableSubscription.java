package io.soulsdk.model.dto;

import java.util.List;

/**
 * AvailableSubscription entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class AvailableSubscription {

    private String productName;
    private int order;
    private SubscriptionsDuration duration;
    private List<String> services;
    private boolean autoRenewable;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public SubscriptionsDuration getDuration() {
        return duration;
    }

    public void setDuration(SubscriptionsDuration duration) {
        this.duration = duration;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public boolean isAutoRenewable() {
        return autoRenewable;
    }

    public void setAutoRenewable(boolean autoRenewable) {
        this.autoRenewable = autoRenewable;
    }
}

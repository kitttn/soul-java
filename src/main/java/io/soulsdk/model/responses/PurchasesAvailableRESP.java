package io.soulsdk.model.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.soulsdk.model.dto.SubscriptionBundle;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain information about available subscription for Current User
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class PurchasesAvailableRESP extends GeneralResponse {

    public PurchasesAvailableRESP() {
    }

    @SerializedName("bundles")
    private List<SubscriptionBundle> subscriptionBundles;


    public List<SubscriptionBundle> getSubscriptionBundles() {
        return subscriptionBundles;
    }

    public void setSubscriptionBundles(List<SubscriptionBundle> subscriptionBundles) {
        this.subscriptionBundles = subscriptionBundles;
    }
}

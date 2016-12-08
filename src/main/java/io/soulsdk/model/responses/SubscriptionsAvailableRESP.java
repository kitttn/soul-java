package io.soulsdk.model.responses;

import java.util.List;

import io.soulsdk.model.dto.AvailableSubscription;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain information about available subscription for Current User
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class SubscriptionsAvailableRESP extends GeneralResponse {

    public SubscriptionsAvailableRESP() {
    }

    private List<AvailableSubscription> availableSubscriptions;

    public List<AvailableSubscription> getAvailableSubscriptions() {
        return availableSubscriptions;
    }

    public void setAvailableSubscriptions(List<AvailableSubscription> availableSubscriptions) {
        this.availableSubscriptions = availableSubscriptions;
    }

}

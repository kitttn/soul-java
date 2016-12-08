package io.soulsdk.model.dto;

/**
 * Subscription Services entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class SubscriptionServices {

    public SubscriptionServices(Membership membership) {
        this.membership = membership;
    }

    public SubscriptionServices() {
    }

    private Membership membership;

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

}

package io.soulsdk.sdk;

import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.requests.AddReceiptREQ;
import io.soulsdk.model.responses.CurrentUserRESP;
import io.soulsdk.model.responses.PurchaseDoneRESP;
import io.soulsdk.model.responses.PurchasesAvailableRESP;
import io.soulsdk.model.responses.SubscriptionsAvailableRESP;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import rx.Observable;

/**
 * <p>
 * Provides a list of methods for working with Google Play Products and Purchasing.
 * </p>
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class SoulPurchases {

    private ServerAPIHelper helper;

    public SoulPurchases(ServerAPIHelper hp) {
        helper = hp;
    }

/*    *//**
     * Returns a list of subscriptions or another kind of products available for purchasing by
     * Current Users.
     *
     * @param soulCallback general {@link SoulCallback} of {@link SubscriptionsAvailableRESP}
     *//*
    public  void getProductsList(SoulCallback<SubscriptionsAvailableRESP> soulCallback) {
        helper.getMySubscriptionAvailable(soulCallback);
    }

    *//**
     * Returns a list of subscriptions or another kind of products available for purchasing by
     * Current Users.
     *
     * @return observable of general {@link SoulResponse} of {@link SubscriptionsAvailableRESP}
     *//*
    public  Observable<SoulResponse<SubscriptionsAvailableRESP>> getProductsList() {
        return helper.getMySubscriptionAvailable(null);
    }*/

    /**
     * Returns a list of subscriptions or another kind of products available for purchasing by
     * Current Users.
     *
     * @param soulCallback general {@link SoulCallback} of {@link SubscriptionsAvailableRESP}
     */
    public void getProductsList(SoulCallback<PurchasesAvailableRESP> soulCallback) {
        helper.getAllPurchases(soulCallback);
    }

    /**
     * Returns a list of subscriptions or another kind of products available for purchasing by
     * Current Users.
     *
     * @return observable of general {@link SoulResponse} of {@link SubscriptionsAvailableRESP}
     */
    public Observable<SoulResponse<PurchasesAvailableRESP>> getProductsList() {
        return helper.getAllPurchases(null);
    }


    /**
     * Submits a Google Play purchase token for validation of purchase was made
     *
     * @param addReceiptREQ object of purchase details for validation
     * @param soulCallback  general {@link SoulCallback} of {@link CurrentUserRESP}
     */
    public void addPurchaseReceipt(AddReceiptREQ addReceiptREQ, SoulCallback<PurchaseDoneRESP> soulCallback) {
        helper.addReceipt(addReceiptREQ, soulCallback);
    }

    /**
     * Submits a Google Play purchase token for validation of purchase was made
     *
     * @param addReceiptREQ object of purchase details for validation
     * @return observable of general {@link SoulResponse} of {@link CurrentUserRESP}
     */
    public Observable<SoulResponse<PurchaseDoneRESP>> addPurchaseReceipt(AddReceiptREQ addReceiptREQ) {
        return helper.addReceipt(addReceiptREQ, null);
    }
}
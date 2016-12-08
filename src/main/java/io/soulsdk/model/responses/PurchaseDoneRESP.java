package io.soulsdk.model.responses;

import java.util.List;

import io.soulsdk.model.dto.PurchaseItem;
import io.soulsdk.model.general.GeneralResponse;

/**
 * //
 *
 * @author Buiarov Iurii
 * @version 0.15
 * @since 01/09/16
 */
public class PurchaseDoneRESP extends GeneralResponse {

    private List<String> bundles;
    private List<PurchaseItem> items;


    public List<String> getBundles() {
        return bundles;
    }

    public void setBundles(List<String> bundles) {
        this.bundles = bundles;
    }

    public List<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItem> items) {
        this.items = items;
    }
}

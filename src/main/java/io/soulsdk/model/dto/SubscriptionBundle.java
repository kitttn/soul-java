package io.soulsdk.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * //
 *
 * @author Buiarov Iurii
 * @version 0.15
 * @since 27/07/16
 */
public class SubscriptionBundle {

    private String bundleName;
    private boolean hasTrial;
    private int order;
    private String description;
    private int purchaseCount;
    private List<Product> products = new ArrayList<>();


    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public boolean isHasTrial() {
        return hasTrial;
    }

    public void setHasTrial(boolean hasTrial) {
        this.hasTrial = hasTrial;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

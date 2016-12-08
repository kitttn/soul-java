package io.soulsdk.model.dto;

/**
 * Subscriptions Duration entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class SubscriptionsDuration {

    private int number;
    private String unit;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

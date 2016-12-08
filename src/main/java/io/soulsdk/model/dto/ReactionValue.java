package io.soulsdk.model.dto;

/**
 * Reaction Value entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class ReactionValue {

    private String value;
    private double expiresTime;
    private double createdTime;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(double expiresTime) {
        this.expiresTime = expiresTime;
    }

    public double getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(double createdTime) {
        this.createdTime = createdTime;
    }
}

package io.soulsdk.model.dto;

/**
 * //
 *
 * @author Buiarov Iurii
 * @version 0.15
 * @since 27/07/16
 */
public class Product {

    private String name;
    private String type;
    private long lifeTimeSeconds;
    private String description;
    private boolean trial;
    private int quantity;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLifeTimeSeconds() {
        return lifeTimeSeconds;
    }

    public void setLifeTimeSeconds(long lifeTimeSeconds) {
        this.lifeTimeSeconds = lifeTimeSeconds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTrial() {
        return trial;
    }

    public void setTrial(boolean trial) {
        this.trial = trial;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package io.soulsdk.model.dto;

/**
 * Meta entity is using mostly for results retrieved by portions
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Meta {

    private int offset;
    private int limit;
    private int total;
    private String sender;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override public String toString() {
        return "{lim: " + limit + "; total: " + total + "; offs: " + offset + "}";
    }
}

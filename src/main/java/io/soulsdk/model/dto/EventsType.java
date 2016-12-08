package io.soulsdk.model.dto;

/**
 * Event Type entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class EventsType {

    private String action;
    private String object;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}

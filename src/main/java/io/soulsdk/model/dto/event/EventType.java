package io.soulsdk.model.dto.event;

/**
 * Event Type entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class EventType {
    public enum Type {
        USER, ENDPOINT, CHAT, REACTION, ME, UNKNOWN
    }

    public enum Action {
        CHANGE, ADDITION, UNKNOWN
    }
    private String action;
    private String object;

    private Type type;


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

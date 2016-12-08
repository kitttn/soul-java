package io.soulsdk.model.dto.event;

/**
 * Event entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Event {
    private int recordId;
    private double time;
    private EventType.Type type;
    private EventType.Action action;
    private EventObject object;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public EventType.Type getType() {
        return type;
    }

    public void setType(EventType.Type type) {
        this.type = type;
    }

    public EventType.Action getAction() {
        return action;
    }

    public void setAction(EventType.Action action) {
        this.action = action;
    }

    public EventObject getObject() {
        return object;
    }

    public void setObject(EventObject object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "{time: " + time + "; action: " + action + "; object: " + type + "; object: " + object + "}";
    }
}
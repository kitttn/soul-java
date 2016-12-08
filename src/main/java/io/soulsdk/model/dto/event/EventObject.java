package io.soulsdk.model.dto.event;

import io.soulsdk.model.dto.Chat;
import io.soulsdk.model.dto.User;

/**
 * @author kitttn
 */

public class EventObject {
    private EventType.Type eventType;
    private User userObject;
    private EventReaction reactionObject;
    private Chat chatObject;
    private EventEndpoint endpointObject;

    public EventType.Type getEventType() {
        return eventType;
    }

    public void setEventType(EventType.Type eventType) {
        this.eventType = eventType;
    }

    public User getUserObject() {
        return userObject;
    }

    public void setUserObject(User userObject) {
        this.userObject = userObject;
    }

    public EventReaction getReactionObject() {
        return reactionObject;
    }

    public void setReactionObject(EventReaction reactionObject) {
        this.reactionObject = reactionObject;
    }

    public Chat getChatObject() {
        return chatObject;
    }

    public void setChatObject(Chat chatObject) {
        this.chatObject = chatObject;
    }

    public EventEndpoint getEndpointObject() {
        return endpointObject;
    }

    public void setEndpointObject(EventEndpoint endpointObject) {
        this.endpointObject = endpointObject;
    }
}

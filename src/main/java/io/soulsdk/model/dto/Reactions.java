package io.soulsdk.model.dto;

/**
 * Reactions entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class Reactions {

    private UserId id; //exists only in Events objects
    private Relation sentByMe;
    private Relation receivedFromUser;

    public Relation getSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(Relation sentByMe) {
        this.sentByMe = sentByMe;
    }

    public Relation getReceivedFromUser() {
        return receivedFromUser;
    }

    public void setReceivedFromUser(Relation receivedFromUser) {
        this.receivedFromUser = receivedFromUser;
    }

    public UserId getId() {
        return id;
    }

    public void setId(UserId id) {
        this.id = id;
    }
}

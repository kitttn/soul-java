package io.soulsdk.model.dto;

/**
 * Chat's information entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class ChatsInfo {

    private int notDeletedByMe;
    private int notDeletedByAnyone;
    private int notExpiredAndNotDeletedByAnyone;

    public int getNotDeletedByMe() {
        return notDeletedByMe;
    }

    public void setNotDeletedByMe(int notDeletedByMe) {
        this.notDeletedByMe = notDeletedByMe;
    }

    public int getNotDeletedByAnyone() {
        return notDeletedByAnyone;
    }

    public void setNotDeletedByAnyone(int notDeletedByAnyone) {
        this.notDeletedByAnyone = notDeletedByAnyone;
    }

    public int getNotExpiredAndNotDeletedByAnyone() {
        return notExpiredAndNotDeletedByAnyone;
    }

    public void setNotExpiredAndNotDeletedByAnyone(int notExpiredAndNotDeletedByAnyone) {
        this.notExpiredAndNotDeletedByAnyone = notExpiredAndNotDeletedByAnyone;
    }
}

package io.soulsdk.model.dto;

/**
 * NotificationTokens entity with tokens for sending push notification from server
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class NotificationTokens {

    private String GCM;
    private String APNS;

    public String getGCM() {
        return GCM;
    }

    public void setGCM(String GCM) {
        this.GCM = GCM;
    }

    public String getAPNS() {
        return APNS;
    }

    public void setAPNS(String APNS) {
        this.APNS = APNS;
    }

}

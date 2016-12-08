package io.soulsdk.model.dto;

/**
 * Authorization entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Authorization {

    private String sessionToken;
    private Double expiresTime;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Double getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Double expiresTime) {
        this.expiresTime = expiresTime;
    }

}

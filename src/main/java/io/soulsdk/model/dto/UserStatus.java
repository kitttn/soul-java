package io.soulsdk.model.dto;

/**
 * @author kitttn
 */

public class UserStatus {
    private String status = "";
    private String userId = "";

    public UserStatus(String status, String userId) {
        this.status = status;
        this.userId = userId;
    }

    public UserStatus() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

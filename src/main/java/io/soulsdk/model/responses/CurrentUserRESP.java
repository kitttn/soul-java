package io.soulsdk.model.responses;

import com.google.gson.annotations.SerializedName;

import io.soulsdk.model.dto.User;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain information about Current User
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class CurrentUserRESP extends GeneralResponse {

    @SerializedName("me")
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}

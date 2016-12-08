package io.soulsdk.model.responses;

import io.soulsdk.model.dto.User;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain information about certain User
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class UserRESP extends GeneralResponse {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
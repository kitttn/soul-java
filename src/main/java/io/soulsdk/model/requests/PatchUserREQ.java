package io.soulsdk.model.requests;

import io.soulsdk.model.dto.NotificationTokens;
import io.soulsdk.model.dto.UsersParameters;
import io.soulsdk.model.general.GeneralRequest;

/**
 * HTTP-request body object for patching Current User
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class PatchUserREQ extends GeneralRequest {

    private NotificationTokens notificationTokens;
    private UsersParameters parameters;

    public NotificationTokens getNotificationTokens() {
        return notificationTokens;
    }

    public void setNotificationTokens(NotificationTokens notificationTokens) {
        this.notificationTokens = notificationTokens;
    }

    public UsersParameters getParameters() {
        return parameters;
    }

    public void setParameters(UsersParameters parameters) {
        this.parameters = parameters;
    }
}

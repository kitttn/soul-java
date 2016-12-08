package io.soulsdk.model.responses;

import io.soulsdk.model.dto.Authorization;
import io.soulsdk.model.dto.ObjectCount;
import io.soulsdk.model.dto.User;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain information about successful authorization and Current User
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class AuthorizationResponse extends GeneralResponse {

    private User me;

    private ObjectCount objectCount;

    private Authorization authorization;

    private Integer providerId;

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public AuthorizationResponse() {
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public ObjectCount getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(ObjectCount objectCount) {
        this.objectCount = objectCount;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
}

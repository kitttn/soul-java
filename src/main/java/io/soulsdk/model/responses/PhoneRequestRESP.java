package io.soulsdk.model.responses;

import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object successful phone authorization request
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class PhoneRequestRESP extends GeneralResponse {

    private String status;
    private int providerId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
}

package io.soulsdk.model.requests;

import io.soulsdk.model.general.GeneralRequest;

/**
 * HTTP-request body object for reporting some user
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class ReportUserREQ extends GeneralRequest {

    private String reason;
    private String comment;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

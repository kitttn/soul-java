package io.soulsdk.model.requests;

import com.google.gson.annotations.SerializedName;

import io.soulsdk.model.general.GeneralRequest;

/**
 * HTTP-request body object for sending reaction
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class ReactionREQ extends GeneralRequest {

    private String value;
    @SerializedName("expiresTime")
    private long expiresTimeInSec;

    public ReactionREQ() {
    }

    public ReactionREQ(String value, long expiresTimeInSec) {
        this.value = value;
        this.expiresTimeInSec = expiresTimeInSec;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getExpiresTimeInSec() {
        return expiresTimeInSec;
    }

    public void setExpiresTimeInSec(long expiresTimeInSec) {
        this.expiresTimeInSec = expiresTimeInSec;
    }
}

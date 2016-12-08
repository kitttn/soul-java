package io.soulsdk.model.dto.chat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * @author kitttn
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ChatMessage {

    @JsonProperty("m") @SerializedName("m")
    private String message = "";
    @JsonProperty("t") @SerializedName("t")
    private double timestamp = 0;
    private String id = "";
    @JsonProperty("u") @SerializedName("u")
    private String userId = "";
    @JsonIgnore
    private transient String channelName = "";
    @JsonIgnore
    private transient String status = "sent";
    public ChatMessage(String channelName, String message) {
        this.message = message;
        this.channelName = channelName;
    }

    public static ChatMessage createFromJson(String jsonString) {
        return new Gson().fromJson(prepareJSON(jsonString), ChatMessage.class);
    }

    static String prepareJSON(String unprepared) {
        return unprepared;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId != null ? userId : "";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @JsonIgnore
    public String getUniqueId() {
        return "{" + id + "_" + userId + "_@" + channelName + "}";
    }

    @JsonIgnore
    public Type getType() {
        return Type.SIMPLE;
    }

    @JsonIgnore
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[id: " + id + "; msg: " + message + "; type: " + getType() + "; time: " + timestamp + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ChatMessage) {
            ChatMessage msg = ((ChatMessage) o);
            return msg.getId().equals(getId())
                    && msg.getUserId().equals(getUserId())
                    && msg.getChannelName().equals(getChannelName());
        }
        return super.equals(o);
    }

    public enum Type {
        SIMPLE, PHOTO, LOCATION, READ, SYSTEM, DELIVERED
    }
}

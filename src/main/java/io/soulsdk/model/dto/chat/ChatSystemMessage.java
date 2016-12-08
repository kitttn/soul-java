package io.soulsdk.model.dto.chat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * @author kitttn
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ChatSystemMessage extends ChatMessage {
    @JsonProperty("sys") @SerializedName("sys")
    private String system = "system";

    @Override public Type getType() {
        return Type.SYSTEM;
    }

    public ChatSystemMessage(String channelName, String message) {
        super(channelName, message);
    }

    public static ChatSystemMessage createFromJson(String jsonString) {
        return new Gson().fromJson(prepareJSON(jsonString), ChatSystemMessage.class);
    }
}

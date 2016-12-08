package io.soulsdk.model.dto.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

/**
 * @author kitttn
 */
public class ChatReadMessage extends ChatMessage {
	@JsonProperty("rt")
	double rt;

	public ChatReadMessage(ChatMessage whichIsRead, double readAt) {
		super(whichIsRead.getChannelName(), "");
		this.rt = readAt;
		setUserId(whichIsRead.getUserId());
		setTimestamp(whichIsRead.getTimestamp());
		setStatus("viewed");
	}

	@Override @JsonIgnore
	public Type getType() {
		return Type.READ;
	}

	public static ChatReadMessage createFromJson(String json) {
		return new Gson().fromJson(prepareJSON(json), ChatReadMessage.class);
	}
}
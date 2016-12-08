package io.soulsdk.model.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

/**
 * @author kitttn
 */
public class ChatDeliveryMessage extends ChatMessage {
	@JsonProperty("d")
	ChatMessage d;

	public ChatDeliveryMessage(ChatMessage delivered) {
		super("", "");
		this.d = delivered;
	}

	@Override
	public Type getType() {
		return Type.DELIVERED;
	}

	public static ChatDeliveryMessage createFromJson(String json) {
		return new Gson().fromJson(prepareJSON(json), ChatDeliveryMessage.class);
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public ChatMessage getDelivered() {
		return d;
	}

	public void setDelivered(ChatMessage delivered) {
		this.d = delivered;
	}
}

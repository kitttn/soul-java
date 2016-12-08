package io.soulsdk.model.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * @author kitttn
 */
public class ChatLocationMessage extends ChatMessage {
	@JsonProperty("lat") @SerializedName("lat")
	private double lat = 0;
	@JsonProperty("lng") @SerializedName("lng")
	private double lng = 0;

	public ChatLocationMessage(String chatId, String message, double lat, double lng) {
		super(chatId, message);
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public static ChatLocationMessage createFromJson(String jsonString) {
		return new Gson().fromJson(prepareJSON(jsonString), ChatLocationMessage.class);
	}

	@Override
	public Type getType() {
		return Type.LOCATION;
	}
}

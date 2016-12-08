package io.soulsdk.model.dto.chat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * @author kitttn
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ChatPhotoMessage extends ChatMessage {
	@JsonProperty("p") @SerializedName("p")
	private String photoId = "";
	@JsonProperty("pa") @SerializedName("pa")
	private String photoAlbum = "";

	public ChatPhotoMessage(String chatId, String message, String photoId, String photoAlbum) {
		super(chatId, message);
		this.photoId = photoId;
		this.photoAlbum = photoAlbum;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getPhotoAlbum() {
		return photoAlbum;
	}

	public void setPhotoAlbum(String photoAlbum) {
		this.photoAlbum = photoAlbum;
	}

	public static ChatPhotoMessage createFromJson(String jsonString) {
		return new Gson().fromJson(prepareJSON(jsonString), ChatPhotoMessage.class);
	}

	@Override
	public Type getType() {
		return Type.PHOTO;
	}
}

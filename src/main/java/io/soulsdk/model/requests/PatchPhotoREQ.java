package io.soulsdk.model.requests;

import com.google.gson.annotations.SerializedName;

import io.soulsdk.model.general.GeneralRequest;

/**
 * HTTP-request body object for patching a photo
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class PatchPhotoREQ  extends GeneralRequest {

    private int order;
    private boolean mainPhoto;
    private Float expiresTime;
    @SerializedName("album")
    private String albumId;


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(boolean mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public Float getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Float expiresTime) {
        this.expiresTime = expiresTime;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}

package io.soulsdk.model.responses;

import io.soulsdk.model.dto.Photo;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain information about certain Photo
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class PhotoRESP  extends GeneralResponse {

    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

}

package io.soulsdk.model.responses;

import io.soulsdk.model.dto.Album;
import io.soulsdk.model.dto.Meta;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain information about certain Album
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class AlbumRESP extends GeneralResponse {

    private Album album;
    private Meta _meta;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Meta get_meta() {
        return _meta;
    }

    public void set_meta(Meta _meta) {
        this._meta = _meta;
    }

}

package io.soulsdk.model.responses;

import java.util.List;

import io.soulsdk.model.dto.Album;
import io.soulsdk.model.dto.Meta;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain list of Albums
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class AlbumsRESP extends GeneralResponse {

    private List<Album> albums;
    private Meta _meta;

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public Meta get_meta() {
        return _meta;
    }

    public void set_meta(Meta _meta) {
        this._meta = _meta;
    }

}

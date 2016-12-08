package io.soulsdk.model.requests;

import io.soulsdk.model.dto.AlbumParameters;
import io.soulsdk.model.general.GeneralRequest;

/**
 * HTTP-request body object for creating new album
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class CreateNewAlbumREQ  extends GeneralRequest {

    private String name;
    private int order;
    private String privacy;
    private AlbumParameters parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public AlbumParameters getParameters() {
        return parameters;
    }

    public void setParameters(AlbumParameters parameters) {
        this.parameters = parameters;
    }
}

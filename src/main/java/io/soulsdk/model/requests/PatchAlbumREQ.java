package io.soulsdk.model.requests;

import io.soulsdk.model.dto.AlbumParameters;
import io.soulsdk.model.general.GeneralRequest;

/**
 * HTTP-request body object for patching an album
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class PatchAlbumREQ extends GeneralRequest {

    private String name;
    private String privacy;
    private int order;
    private AlbumParameters parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public AlbumParameters getParameters() {
        return parameters;
    }

    public void setParameters(AlbumParameters parameters) {
        this.parameters = parameters;
    }

}

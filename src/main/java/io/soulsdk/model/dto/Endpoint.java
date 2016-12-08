package io.soulsdk.model.dto;

/**
 * Endpoint entity is using for Events
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Endpoint {

    private String uri;
    private String type;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

package io.soulsdk.model.dto.event;

/**
 * @author kitttn
 */

public class EventEndpoint {
    public static final String RECOMMENDATIONS = "/users/recommendations";
    private String uri;
    private String type = "newItems";

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

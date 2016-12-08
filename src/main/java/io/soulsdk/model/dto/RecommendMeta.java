package io.soulsdk.model.dto;

import io.soulsdk.sdk.SoulUsers;

/**
 * Meta entity is using only for {@link SoulUsers#getNextSearchResult()} or
 * {@link SoulUsers#repeatLastSearchResult()}
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class RecommendMeta {

    private String sessionName;
    private String uniqueToken;

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getUniqueToken() {
        return uniqueToken;
    }

    public void setUniqueToken(String uniqueToken) {
        this.uniqueToken = uniqueToken;
    }
}

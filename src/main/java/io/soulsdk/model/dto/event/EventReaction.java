package io.soulsdk.model.dto.event;

import io.soulsdk.model.dto.Relation;

/**
 * @author kitttn
 */

public class EventReaction {
    private String userId;
    private Relation relation;
    // Reactions from userId

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }
}

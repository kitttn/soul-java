package io.soulsdk.model.tasks;

import io.soulsdk.model.requests.ReactionREQ;

/**
 * Created by Buiarov on 24/03/16.
 */
public class ReactionTask extends GeneralTask{

    private String userId;
    private String reactingType;
    private ReactionREQ reactionREQ;

    public ReactionTask(String userId, String reactingType, ReactionREQ reactionREQ) {
        this.userId = userId;
        this.reactingType = reactingType;
        this.reactionREQ = reactionREQ;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReactingType() {
        return reactingType;
    }

    public void setReactingType(String reactingType) {
        this.reactingType = reactingType;
    }

    public ReactionREQ getReactionREQ() {
        return reactionREQ;
    }

    public void setReactionREQ(ReactionREQ reactionREQ) {
        this.reactionREQ = reactionREQ;
    }
}

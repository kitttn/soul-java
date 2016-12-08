package io.soulsdk.model.dto;

/**
 * Relation entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class Relation {

    public Relation() {
        likes = new ReactionValue();
        blocking = new ReactionValue();
    }

    private ReactionValue likes;
    private ReactionValue blocking;

    public ReactionValue getLikes() {
        return likes;
    }

    public void setLikes(ReactionValue likes) {
        this.likes = likes;
    }

    public ReactionValue getBlocking() {
        return blocking;
    }

    public void setBlocking(ReactionValue blocking) {
        this.blocking = blocking;
    }

}

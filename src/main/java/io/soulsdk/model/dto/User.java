package io.soulsdk.model.dto;

import com.google.gson.Gson;

import java.util.List;

/**
 * User entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class User {

    private String id;
    private int recordId;
    private NotificationTokens notificationTokens;
    private SubscriptionServices subscriptionServices;
    private UsersParameters parameters;
    private List<Album> albums;
    private Reactions reactions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public NotificationTokens getNotificationTokens() {
        return notificationTokens;
    }

    public void setNotificationTokens(NotificationTokens notificationTokens) {
        this.notificationTokens = notificationTokens;
    }

    public SubscriptionServices getSubscriptionServices() {
        return subscriptionServices;
    }

    public void setSubscriptionServices(SubscriptionServices subscriptionServices) {
        this.subscriptionServices = subscriptionServices;
    }

    public UsersParameters getParameters() {
        return parameters;
    }

    public void setParameters(UsersParameters parameters) {
        this.parameters = parameters;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public Reactions getReactions() {
        return reactions;
    }

    public void setReactions(Reactions reactions) {
        this.reactions = reactions;
    }

    @Override public String toString() {
        return "{ " + id + " }";
    }
}

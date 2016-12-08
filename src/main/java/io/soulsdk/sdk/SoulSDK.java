package io.soulsdk.sdk;

import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.network.managers.Credentials;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import io.soulsdk.util.error.ErrorInterceptor;

/**
 * Initial class. Start using the SDK with this class.
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class SoulSDK {

    private SoulConfigs configs;
    private SoulAuth auth;
    private SoulCurrentUser currentUser;
    private SoulMedia media;
    private SoulUsers users;
    private SoulReactions reactions;
    private SoulCommunication communication;
    private SoulEvents events;
    private SoulPurchases purchases;

    public SoulSDK() {
    }

    /**
     * The initial method. Start using the SDK with this method. In this method all Soul Modules
     * will be initialized: {@link SoulAuth}, {@link SoulCurrentUser}, {@link SoulMedia},
     * {@link SoulUsers}, {@link SoulReactions}, {@link SoulCommunication}, {@link SoulEvents},
     * {@link SoulPurchases}, {@link SoulSystem}
     *
     * @param apiKey  the api key of your project
     */

    public void initialize(String serverHost, String apiKey, String chatSalt, Credentials credentials) {
        ServerAPIHelper helper = new ServerAPIHelper(serverHost, apiKey, credentials);

        configs = new SoulConfigs(serverHost);
        auth = new SoulAuth(helper);
        helper.setSoulAuth(auth);
        currentUser = new SoulCurrentUser(configs, helper);
        media = new SoulMedia(helper);
        users = new SoulUsers(helper);
        reactions = new SoulReactions(configs, helper);
        communication = new SoulCommunication(currentUser, media, configs, helper, chatSalt);
        events = new SoulEvents(helper);
        purchases = new SoulPurchases(helper);
    }

/*    public void initErrorLogger(Context context, String dns) {
        SentryCatcher.init(context, dns);
    }*/

    public SoulConfigs getConfigs() {
        return configs;
    }

    public void setConfigs(SoulConfigs configs) {
        this.configs = configs;
    }

    public SoulAuth getAuth() {
        return auth;
    }

    public void setAuth(SoulAuth auth) {
        this.auth = auth;
    }

    public SoulCurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(SoulCurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public SoulMedia getMedia() {
        return media;
    }

    public void setMedia(SoulMedia media) {
        this.media = media;
    }

    public SoulUsers getUsers() {
        return users;
    }

    public void setUsers(SoulUsers users) {
        this.users = users;
    }

    public SoulReactions getReactions() {
        return reactions;
    }

    public void setReactions(SoulReactions reactions) {
        this.reactions = reactions;
    }

    public SoulCommunication getCommunication() {
        return communication;
    }

    public void setCommunication(SoulCommunication communication) {
        this.communication = communication;
    }

    public SoulEvents getEvents() {
        return events;
    }

    public void setEvents(SoulEvents events) {
        this.events = events;
    }

    public SoulPurchases getPurchases() {
        return purchases;
    }

    public void setPurchases(SoulPurchases purchases) {
        this.purchases = purchases;
    }

    public void addErrorInterceptor(ErrorInterceptor interceptor) {
        SoulResponse.setInterceptor(interceptor);
    }
}

package io.soulsdk.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.soulsdk.model.dto.chat.ChatMessage;
import io.soulsdk.model.general.SoulParameterObject;

/**
 * Chat entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Chat {

    private String id; //	string	id of the chat
    @SerializedName("participants")
    private List<UserStatus> users; //	array of strings	array of user ids, representing users that joined this chat including current user
    private SoulParameterObject parameters; //	dict	schemaless dictionary of parameters, can be used to store some shared data between chat users, e.g. user chat statuses
    private String channelName; //	string	unique chat channel name that the client should connect to
    private float expiresTime; //	float	time of chat expiration, unixtimestamp
    private long unreadCount = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UserStatus> getUsers() {
        return users;
    }

    public void setUsers(List<UserStatus> users) {
        this.users = users;
    }

    public SoulParameterObject getParameters() {
        return parameters;
    }

    public void setParameters(SoulParameterObject parameters) {
        this.parameters = parameters;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public float getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(float expiresTime) {
        this.expiresTime = expiresTime;
    }
}

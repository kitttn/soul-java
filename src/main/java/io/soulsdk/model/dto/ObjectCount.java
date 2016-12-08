package io.soulsdk.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * ObjectCount entity is using for getting briefly information about countable objects
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class ObjectCount {

    @SerializedName("chats")
    private ChatsInfo chatsInfo;

    public ChatsInfo getChatsInfo() {
        return chatsInfo;
    }

    public void setChatsInfo(ChatsInfo chatsInfo) {
        this.chatsInfo = chatsInfo;
    }
}

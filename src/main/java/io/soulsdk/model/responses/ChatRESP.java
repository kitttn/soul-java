package io.soulsdk.model.responses;

import io.soulsdk.model.dto.Chat;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain information about certain Chat
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class ChatRESP extends GeneralResponse {

    public ChatRESP(Chat chat) {
        this.chat = chat;
    }

    public ChatRESP() {
    }

    private Chat chat;

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}

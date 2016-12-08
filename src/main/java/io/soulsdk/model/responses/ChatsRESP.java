package io.soulsdk.model.responses;

import java.util.List;

import io.soulsdk.model.dto.Chat;
import io.soulsdk.model.dto.Meta;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain list of Chats
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class ChatsRESP extends GeneralResponse {

    private List<Chat> chats;
    private Meta _meta;

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public Meta get_meta() {
        return _meta;
    }

    public void set_meta(Meta _meta) {
        this._meta = _meta;
    }

}

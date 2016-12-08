package io.soulsdk.sdk.helpers.chat;

import io.soulsdk.model.dto.chat.ChatDeliveryMessage;
import io.soulsdk.model.dto.chat.ChatLocationMessage;
import io.soulsdk.model.dto.chat.ChatMessage;
import io.soulsdk.model.dto.chat.ChatPhotoMessage;
import io.soulsdk.model.dto.chat.ChatReadMessage;
import io.soulsdk.model.dto.chat.ChatSystemMessage;

/**
 * @author kitttn
 */
public class ChatMessageFabric {
    public static ChatMessage create(String json) {
        //Log.e("CHAT_DEB", json);
        // checking delivery and read messages at first
        if (json == null)
            throw new Error("Can't create message!");

        if (json.contains("\"d\":"))
            return ChatDeliveryMessage.createFromJson(json);

        if (json.contains("\"rt\":"))
            return ChatReadMessage.createFromJson(json);

        if (json.contains("\"sys\":"))
            return ChatSystemMessage.createFromJson(json);

        // okay, it seems everything is good and it is usual message
        if (json.contains("\"pa\":"))
            return ChatPhotoMessage.createFromJson(json);

        if (json.contains("\"lat\":"))
            return ChatLocationMessage.createFromJson(json);
        return ChatMessage.createFromJson(json);
    }
}

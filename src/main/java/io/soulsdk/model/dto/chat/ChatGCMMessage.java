package io.soulsdk.model.dto.chat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kitttn
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class ChatGCMMessage extends ChatMessage {
    private Map<String, ChatMessage> pn_gcm = new HashMap<>();

    public ChatGCMMessage(ChatMessage messageToSend) {
        super(messageToSend.getChannelName(), "");
        setId(messageToSend.getId());
        setTimestamp(messageToSend.getTimestamp());
        setUserId(messageToSend.getUserId());

        pn_gcm.put("data", messageToSend);
    }
}

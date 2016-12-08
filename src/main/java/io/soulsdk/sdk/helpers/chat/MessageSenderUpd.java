package io.soulsdk.sdk.helpers.chat;

import com.pubnub.api.PubNub;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import io.soulsdk.model.dto.chat.ChatMessage;
import log.Log;

/**
 * @author kitttn
 */
public class MessageSenderUpd {
    private static final String TAG = "MessageSenderUpd";
    private Hashtable<String, Boolean> permissionMap;
    private BlockingQueue<MessageToSend> messageQueue;
    private Thread sendingThread;
    private PubNub pubNub;
    private Runnable messageSendingThread = () -> {
        boolean working = pubNub != null;
        while (working) {
            try {
                MessageToSend msg = messageQueue.take();
                Log.i(TAG, "SendingThread: Got new message: " + msg);
                pubNub.publish()
                        .channel(msg.adressee)
                        .message(msg.body)
                        .sync();
                Log.i(TAG, "SendingThread: Sent!");
                messageQueue.remove(msg);
            } catch (Exception e) {
                working = false;
                Log.i(TAG, "SendingThread: Crashed with exception: " + e);
                e.printStackTrace();
            }
        }
    };

    public MessageSenderUpd(PubNub pubNub) {
        permissionMap = new Hashtable<>();
        this.pubNub = pubNub;
        sendingThread = new Thread(messageSendingThread);
        messageQueue = new ArrayBlockingQueue<>(100);
        sendingThread.start();
    }

    public void send(String channelName, Object message) {
        if (isAllowed(channelName)) {
            Log.i(TAG, "send: Sending to channel=" + channelName);
            messageQueue.offer(new MessageToSend(channelName, message));
            return;
        }

        Log.i(TAG, "send: Not allowed to send to channel=" + channelName);
    }

    public void setAllowed(String channelName, boolean allowed) {
        permissionMap.put(channelName, allowed);
        Log.i(TAG, "setAllowed: >> " + allowed + " to " + channelName);
    }

    public void setAllowed(List<String> channels, boolean allowed) {
        for (String channel : channels)
            setAllowed(channel, allowed);
    }

    private boolean isAllowed(String channelName) {
        Boolean value = permissionMap.get(channelName);
        return value != null && value;
    }

    private class MessageToSend implements Serializable {
        private Object body;
        private String adressee;

        MessageToSend(String adressee, Object msg) {
            body = msg;
            this.adressee = adressee;
        }
    }
}

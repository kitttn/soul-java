package io.soulsdk.sdk.helpers.chat;


import log.Log;

import com.pubnub.api.PubNub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.soulsdk.model.dto.Chat;
import io.soulsdk.model.dto.chat.ChatMessage;
import io.soulsdk.util.storage.TmpStorage;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static io.soulsdk.sdk.helpers.chat.ChatsHelper.CHAT_MESSAGE_TIMESTAMP_PREFIX;

/**
 * @author kitttn
 */
public class MessageReceiver {
    private static final String TAG = "MessageReceiver";
    private static MessageReceiver instance;
    // static variables
    private PubNub pubnub;
    private PubnubCallback callback;
    private ChatMessageEncoder encoder;
    // instance variables
    private HashMap<String, PublishSubject<ChatMessage>> subjectMap = new HashMap<>();

    private MessageReceiver() {
    }

    public static MessageReceiver getInstance() {
        if (instance == null)
            instance = new MessageReceiver();
        return instance;
    }

    /**
     * Initializes API with provided PubNub instance
     */
    public void init(ChatMessageEncoder encoder, PubNub pubnub) {
        if (this.pubnub != null)
            return;
        this.encoder = encoder;
        this.pubnub = pubnub;
        // TODO: show connection problems here?
        callback = new PubnubCallback((pubNub, message) -> {
            Log.i(TAG, "listenForNew: New message: " + message);
            String channelName = message.getChannel();
            ChatMessage msg = createFromText(channelName, message.getMessage().toString());
            //Log.i(TAG, "listenForNew: New message from channel " + channelName + ": " + msg);
            getInstance().saveTime(channelName, message.getTimetoken());
            getInstance().dispatchMessage(channelName, msg);
        });

        pubnub.addListener(callback);

        checkCallback();
    }


    public void checkCallback() {
        System.out.println("PING - callback = " + (getInstance().callback != null));
    }

    private ChatMessage createFromText(String channelName, String text) {
        String encodedMsg = text;
        if (encodedMsg.startsWith("\"") && encodedMsg.endsWith("\"") && encodedMsg.length() > 2) {
            //Log.i(TAG, "init: Trimming quotes...");
            encodedMsg = encodedMsg.substring(1, encodedMsg.length() - 1);
        }
        Log.i(TAG, "init: Got encoded msg: " + encodedMsg);
        ChatMessage msg = encoder.decode(channelName, encodedMsg);
        if (msg != null)
            msg.setChannelName(channelName);
        return msg;
    }

    /**
     * Loads message history after the given {@link ChatMessage} in reverse order - newest messages first
     *
     * @param message message, after which you should load history
     * @return stream with messages in history
     */
    public Observable<ChatMessage> loadHistoryAfter(String channelName, ChatMessage message) {
        return loadHistoryAfter(channelName, (long) (message.getTimestamp() * 1e10));
    }

    /**
     * Loads message history after the given {@link ChatMessage} in reverse order - newest messages first
     *
     * @param messageTime timestamp of the message, after which you should load history
     * @return stream with messages in history
     */
    // TODO: add paging for loading more than 100 messages
    public Observable<ChatMessage> loadHistoryAfter(String channelName, long messageTime) {
        PublishSubject<ChatMessage> sub = getSub(channelName);
        pubnub.history()
                .channel(channelName)
                .count(100)
                .end(messageTime + 1)
                .async(new MessagerCallback<>((pnHistoryResult, pnStatus) -> {
                    Log.i(TAG, "loadHistoryAfter: Loaded history ");
                    saveTime(channelName, pnHistoryResult.getEndTimetoken());

                    Observable.from(pnHistoryResult.getMessages())
                            .map(res -> res.getEntry().toString())
                            .map(txt -> createFromText(channelName, txt))
                            .subscribeOn(Schedulers.io())
                            .subscribe(sub::onNext, sub::onError);
                }));

        return sub;
    }

    public Observable<List<ChatMessage>> getHistory(String channelName, long afterTime, int count) {
        PublishSubject<List<ChatMessage>> sub = PublishSubject.create();
        if (count == 0)
            return Observable.just(new ArrayList<>());
        try {
            pubnub.history()
                    .channel(channelName)
                    .count(count)
                    .end(afterTime + 1)
                    .async(new MessagerCallback<>((pnHistoryResult, pnStatus) -> {
                        Log.i(TAG, "getHistory: Loaded history ");
                        if (pnHistoryResult == null || pnHistoryResult.getMessages().size() == 0) {
                            sub.onNext(new ArrayList<>());
                            sub.onCompleted();
                            return;
                        }

                        saveTime(channelName, pnHistoryResult.getEndTimetoken());

                        Observable.from(pnHistoryResult.getMessages())
                                .subscribeOn(Schedulers.io())
                                .map(res -> res.getEntry().toString())
                                .map(txt -> createFromText(channelName, txt))
                                .filter(msg -> msg != null)
                                .toList()
                                .subscribe(sub);
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sub;
    }

    /**
     * Subscribe on this chat to receive all further messages from this chat
     *
     * @param chat you wish to subscribe on
     * @return stream of the possible future messages
     */
    public Observable<ChatMessage> listenForNew(Chat chat) {
        return listenForNew(chat.getChannelName());
    }

    /**
     * Subscribe on the chat with this channelName to receive all further messages from this chat
     *
     * @param channelName name of the channel you wish to subscribe on
     * @return stream of the possible future messages
     */
    public Observable<ChatMessage> listenForNew(String channelName) {
        pubnub.subscribe()
                .channels(Collections.singletonList(channelName))
                .execute();
        return getSub(channelName);
    }

    /**
     * Sends message to subscriber, listening to chat with channelName
     *
     * @param channelName name of the chat to send message
     * @param message     {@link ChatMessage} to send
     */
    public void dispatchMessage(String channelName, ChatMessage message) {
        PublishSubject<ChatMessage> sub = getSub(channelName);
        Log.i(TAG, "dispatchMessage: Sending it to " + sub + "...");
        sub.onNext(message);
    }

    // ======================== private class helpers ==========================

    /**
     * Stops receiving messages from chat with name=channelName
     *
     * @param channelName
     */
    public void unsubscribe(String channelName) {
        Log.i(TAG, "unsubscribe: Stopping receiving messages from " + channelName);
        pubnub.unsubscribe()
                .channels(Collections.singletonList(channelName))
                .execute();
        subjectMap.remove(channelName);
    }

    private PublishSubject<ChatMessage> getSub(String channelName) {
        PublishSubject<ChatMessage> stream = subjectMap.get(channelName);
        if (stream == null || stream.hasCompleted() || stream.hasThrowable())
            subjectMap.put(channelName, PublishSubject.create());
        return subjectMap.get(channelName);
    }

    private void saveTime(String channelName, long time) {
        if (time == 0)
            return;
        Log.i(TAG, "saveTime: Last message received at " + time + ", saving...");
        TmpStorage.save(CHAT_MESSAGE_TIMESTAMP_PREFIX + channelName, time);
    }
}

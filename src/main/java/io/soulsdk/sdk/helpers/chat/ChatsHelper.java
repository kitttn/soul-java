package io.soulsdk.sdk.helpers.chat;

import com.google.gson.Gson;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.enums.PNPushType;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.soulsdk.model.dto.Chat;
import io.soulsdk.model.dto.Photo;
import io.soulsdk.model.dto.UserStatus;
import io.soulsdk.model.dto.UsersLocation;
import io.soulsdk.model.dto.chat.ChatGCMMessage;
import io.soulsdk.model.dto.chat.ChatLocationMessage;
import io.soulsdk.model.dto.chat.ChatMessage;
import io.soulsdk.model.dto.chat.ChatPhotoMessage;
import io.soulsdk.model.general.SoulError;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.requests.CreateNewAlbumREQ;
import io.soulsdk.sdk.SoulCurrentUser;
import io.soulsdk.sdk.SoulMedia;
import io.soulsdk.sdk.SoulSystem;
import io.soulsdk.util.storage.TmpStorage;
import log.Log;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author kitttn
 */
public class ChatsHelper {
    public static final int MAX_RETRIES = 3;
    private static final String TAG = "ChatsHelper";
    private static final String CHAT_MESSAGE_ID_PREFIX = "CHAT_MSG_ID_";
    public static final String CHAT_MESSAGE_TIMESTAMP_PREFIX = "MSG_TIMESTAMP__";
    private final String userId;
    private final HashMap<String, List<String>> partners = new HashMap<>();
    private final HashMap<String, Double> lastIds = new HashMap<>();

    private final MessageSenderUpd sender;
    private final MessageReceiver receiver;
    private PubNub pubnub;
    private Gson gson;

    private String pubKey = "";
    private String subKey = "";
    private String uId = "";
    private SoulCurrentUser currentUser;
    private SoulMedia media;
    private ChatMessageEncoder encoder;

    // private static ChatsHelper instance;
/*
    public static ChatsHelper getInstance() {
        synchronized (TAG) {
            if (instance == null) {
                if ("".equals(pubKey) || "".equals(subKey) || "".equals(uId))
                    throw new Error("Can't initialize receiver. Make sure you call init() first!");
                instance = new ChatsHelper(uId, pubKey, subKey);
            }
            return instance;
        }
    }*/

  /*  public void init(String userId, String publishKey, String subscribeKey) {
        uId = userId;
        pubKey = publishKey;
        subKey = subscribeKey;

        ChatMessageEncoder.init();
    }*/

    public ChatsHelper(String userId, String publishKey, String subscribeKey, String chatSalt, SoulCurrentUser currentUser, SoulMedia media) {
        this.userId = userId;
        this.currentUser = currentUser;
        this.media = media;

        PNConfiguration configuration = new PNConfiguration();
        configuration.setPublishKey(publishKey);
        configuration.setSubscribeKey(subscribeKey);

        pubnub = new PubNub(configuration);
        gson = new Gson();
        encoder = new ChatMessageEncoder(chatSalt);
        MessageReceiver.getInstance().init(encoder, pubnub);
        receiver = MessageReceiver.getInstance();
        sender = new MessageSenderUpd(pubnub);
    }

    public void checkCallback() {
        if (receiver == null) System.out.println("PING - callback =... receiver==null");
        else
            receiver.checkCallback();
    }

    public void connectToGCM(String deviceId) {
        Log.i(TAG, "connectToFCM: Connecting to GCM...");
        Log.i(TAG, "Device id: " + deviceId);
        pubnub.addPushNotificationsOnChannels()
                .channels(Collections.singletonList(userId))
                .deviceId(deviceId)
                .pushType(PNPushType.GCM)
                .async(new MessagerCallback<>((pnPushAddChannelResult, pnStatus) -> {
                    Log.i(TAG, "connectToFCM: Connected successfully: " + pnPushAddChannelResult);
                }));
    }

    public Observable<SoulResponse<ChatMessage>> connectAndListen(String channelName, List<String> partners) {
        // initializing lastId, just in case
        initLastIdIfEmpty(channelName);

        // adding chat participants to partners, excluding myself
        String myId = currentUser.getCurrentUser().getId();
        Observable.from(partners)
                .filter(partner -> !myId.equals(partner))
                .toList()
                .forEach(res -> this.partners.put(channelName, res));

        Object obj = TmpStorage.get(CHAT_MESSAGE_ID_PREFIX + channelName);
        double lastId = obj == null ? -1 : (double) obj;
        lastIds.put(channelName, lastId);

        // SoulStorage.save(SoulStorage.CHAT_MESSAGE_PREFIX + chatId, "{}");
        // SoulStorage.save(SoulStorage.CHAT_ID_PREFIX + chatId, "-1");

        sender.setAllowed(channelName, true);
        sender.setAllowed(partners, true);

        return receiver.listenForNew(channelName)
                .map(msg -> {
                    // loading last messageId from history
                    //Log.i(TAG, "connectAndListen: comparing message Id = " + msg.getId() + " with " + lastIds.get(channelName));
                    if (msg.getUserId().equals(uId) && msg.getTimestamp() > lastIds.get(channelName))
                        lastIds.put(channelName, msg.getTimestamp());
                    return msg;
                })
                .map(SoulResponse::new)
                .subscribeOn(Schedulers.io());
    }

    public Observable<SoulResponse<ChatMessage>> connectAndListen(final Chat chat) {
        if (chat.getUsers().size() > 0) {
            return Observable.from(chat.getUsers())
                    .map(UserStatus::getUserId)
                    .toList()
                    .flatMap(list -> connectAndListen(chat.getChannelName(), list));
        } else {
            Log.e(TAG, "connectAndListen: No users in chat, can't connect!");
            SoulError err = new SoulError("No users in chat!", SoulError.CHATS_ERROR_LOADING);
            throw new Error(err);
        }
    }

    public Observable<List<ChatMessage>> loadHistory(String channelName, int count) {
        // in case we're not connected, init lastId
        initLastIdIfEmpty(channelName);

        Object obj = TmpStorage.get(CHAT_MESSAGE_TIMESTAMP_PREFIX + channelName);
        long timestamp = obj == null ? -1 : (long) obj;
        return receiver
                .getHistory(channelName, timestamp, count)
                .onBackpressureBuffer()
                .map(msgList -> {
                    boolean cont = true;
                    int i = msgList.size();
                    while (--i >= 0 && cont) {
                        ChatMessage msg = msgList.get(i);
                        if (msg.getUserId().equals(uId) && msg.getTimestamp() > lastIds.get(channelName)) {
                            Log.i(TAG, "loadHistory: updating my lastId to " + msg.getId());
                            lastIds.put(channelName, msg.getTimestamp());
                            cont = false;
                        }
                    }

                    return msgList;
                });
    }

    public Observable<List<ChatMessage>> loadHistory(String channelName, int count, boolean fromBeginning) {
        if (fromBeginning)
            TmpStorage.save(CHAT_MESSAGE_TIMESTAMP_PREFIX + channelName, -1);
        return loadHistory(channelName, count);
    }

    /**
     * Sends simple chat message, preprocessing it before adding to message queue
     *
     * @param channelName id of the chat to send message to
     * @param message     text of the message
     */
    public Observable<ChatMessage> sendMessage(String channelName, String message) {
        ChatMessage msg = new ChatMessage(channelName, message);
        return Observable.just(sendMessage(channelName, msg));
    }

    /**
     * Sends custom location, preprocessing it, before adding to the message queue
     *
     * @param channelName id of the chat to send location to
     * @param location    location you wish to share
     */
    public Observable<ChatMessage> sendMessage(String channelName, UsersLocation location, String message) {
        ChatLocationMessage msg = new ChatLocationMessage(channelName, message, location.getLat(), location.getLng());
        return Observable.just(sendMessage(channelName, msg));
    }

    /**
     * Sends photo to the user, preprocessing message and uploading photo, before adding to the message queue
     *
     * @param channelName id of the chat to send photo (link) to
     * @param photo       File with photo you wish to upload
     */
    public void sendMessage(String channelName, File photo, String message) {
        Observable.just(true)
                .subscribeOn(Schedulers.io())
                .flatMap(b -> media.addPhotoToMyAlbum(channelName, photo))
                .flatMap(res -> {
                    Log.i(TAG, "sendMessage: Error? " + res.isErrorHappened());
                    Log.i(TAG, "sendMessage: Error obj: " + res.getError());
                    if (res.isErrorHappened()) {
                        CreateNewAlbumREQ req = new CreateNewAlbumREQ();
                        req.setName(channelName);
                        req.setPrivacy("unlisted");
                        return media.createNewAlbum(req)
                                .flatMap(album -> {
                                    Log.i(TAG, "sendMessage: New album: " + album.getResponse());
                                    return (album.isErrorHappened())
                                            ? Observable.error(album.getError())
                                            : Observable.error(res.getError());
                                });
                    }
                    return Observable.just(res.getResponse());
                })
                .retryWhen(errors -> errors.zipWith(Observable.range(1, MAX_RETRIES), (n, i) -> i))
                .map(res -> {
                    Photo ph = res.getPhoto();
                    ChatPhotoMessage msg = new ChatPhotoMessage(channelName, message, ph.getId(), channelName);
                    msg.setUserId(userId);
                    return sendMessage(channelName, msg);
                });
    }

    /**
     * Sends message directly to the message queue.
     * <p><b>IMPORTANT: </b>If you send complex message types (PHOTO, LOCATION) - make sure you
     * use boxed methods, like sendMessage(channelName, location) or prepare ChatMessage by yourself.
     * Messages sent with this method aren't preprocessed. </p>
     *
     * @param channelName chat id of the message you want to send to
     * @param message     {@link ChatMessage} itself. SDK ensures only time and sender.
     */
    public ChatMessage sendMessage(String channelName, ChatMessage message) {
        long serverTime = SoulSystem.getServerTime();
        message.setTimestamp(serverTime / 1000);
        message.setUserId(userId);
        message.setId("-1");

        // setting id for regular message
        if (lastIds.containsKey(channelName)) {
            double lastMsgId = lastIds.get(channelName);
            message.setId(UUID.randomUUID().toString());

            // saving message and it's id to the SoulStorage
            saveMessageInfo(channelName, message);
            String encoded = encoder.encode(channelName, message);
            sender.send(channelName, encoded);

            // we don't need to send push messages about read status
            if (message.getType() == ChatMessage.Type.READ)
                return message;

            // now sending push messages to all companions
            ChatGCMMessage push = new ChatGCMMessage(message);
            List<String> companions = partners.get(channelName);
            if (companions != null)
                for (String partner : companions)
                    sender.send(partner, push);
        }

        return message;
    }

    public void unsubscribe(String channelName) {
        receiver.unsubscribe(channelName);
        sender.setAllowed(channelName, false);
    }

    // ============ private methods for handling =====================

    private void saveMessageInfo(String channelName, ChatMessage message) {
        Log.i(TAG, "saveMessageInfo: Saving message with id=" + message.getId());
        lastIds.put(channelName, message.getTimestamp());
        TmpStorage.save(CHAT_MESSAGE_ID_PREFIX + channelName, message.getTimestamp());
    }

    private PubNub getPubnub() {
        return pubnub;
    }

    private void initLastIdIfEmpty(String channelName) {
        Double lastId = lastIds.get(channelName);
        if (lastId == null)
            lastIds.put(channelName, -1d);
    }
}

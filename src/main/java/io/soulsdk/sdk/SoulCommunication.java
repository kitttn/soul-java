package io.soulsdk.sdk;

import java.util.List;

import io.soulsdk.model.dto.Chat;
import io.soulsdk.model.dto.MyStatus;
import io.soulsdk.model.dto.chat.ChatMessage;
import io.soulsdk.model.dto.chat.ChatReadMessage;
import io.soulsdk.model.general.GeneralResponse;
import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.responses.ChatRESP;
import io.soulsdk.model.responses.ChatsRESP;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import io.soulsdk.sdk.helpers.chat.ChatsHelper;
import log.Log;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Provides a list of methods for users communication.
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class SoulCommunication {
    private static final String TAG = "SoulCommunication";
    private ServerAPIHelper helper;
    private ChatsHelper chats;
    private SoulCurrentUser currentUser;
    private SoulConfigs configs;
    private SoulMedia media;
    private String chatSalt;


    public SoulCommunication(SoulCurrentUser currentUser, SoulMedia media, SoulConfigs configs, ServerAPIHelper hp, String chatSalt) {
        this.media = media;
        this.chatSalt = chatSalt;
        this.configs = configs;
        this.currentUser = currentUser;
        helper = hp;
    }

    private ChatsHelper getChatsHelper() {
        if (chats == null) {
            String userId = currentUser.getCurrentUser().getId();
            chats = new ChatsHelper(userId, configs.getPublishKey(), configs.getSubscribeKey(), chatSalt, currentUser, media);
        }
        return chats;
    }

    public void checkCallback() {
        if (chats == null) System.out.println("PING - callback =... chat==null");
        else
            chats.checkCallback();
    }

    /**
     * Provides all chats of the current authorized User. The method allows to get results by "pages" using
     * limit and offset. There is a {@link io.soulsdk.model.dto.Meta} in server response {@link ChatsRESP}
     * that will give the information to help requesting pages.
     *
     * @param after        specifies how many results to skip for the first returned result
     * @param limit        specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @param showExpired  if value is 'false' only active chats will be gotten with expiration time more than current server time.
     * @param soulCallback general {@link SoulCallback} of the {@link ChatsRESP}
     */
    public void getAll(Integer after, Integer limit, Boolean showExpired, String myStatus, SoulCallback<ChatsRESP> soulCallback) {
        helper.getAllChats(after, limit, showExpired, myStatus, soulCallback);
    }

    /**
     * Provides all chats of the current authorized User. The method allows to get results by "pages" using
     * limit and offset. There is a {@link io.soulsdk.model.dto.Meta} in server response {@link ChatsRESP}
     * that will give the information to help requesting pages.
     *
     * @param after       specifies how many results to skip for the first returned result
     * @param limit       specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @param showExpired if value is 'false' only active chats will be gotten with expiration time more than current server time.
     * @return {@link SoulResponse} of the {@link ChatsRESP} as observable.
     * It is necessary to call subscribe method of observable to get any result.
     */
    public Observable<SoulResponse<ChatsRESP>> getAll(Integer after, Integer limit, Boolean showExpired, String myStatus) {
        return helper.getAllChats(after, limit, showExpired, myStatus, null);
    }

    /**
     * Provides details of mentioned chat.
     *
     * @param chatId       of the chat
     * @param soulCallback general {@link SoulCallback} of the {@link ChatRESP}
     */
    public void getOne(String chatId, SoulCallback<ChatRESP> soulCallback) {
        helper.getOneChat(chatId, soulCallback);
    }

    /**
     * Provides details of mentioned chat.
     *
     * @param chatId id of the chat
     * @return {@link SoulResponse} of the {@link ChatRESP} as observable.
     * It is necessary to call subscribe method of observable to get any result.
     */
    public Observable<SoulResponse<ChatRESP>> getOne(String chatId) {
        return helper.getOneChat(chatId, null);
    }

    /**
     * Deletes mentioned chat.
     *
     * @param chatId       of the chat
     * @param soulCallback general {@link SoulCallback} of the {@link Boolean}
     */
    public void patch(String chatId, String myStatus, SoulCallback<GeneralResponse> soulCallback) {
        helper.patchChat(chatId, new MyStatus(myStatus), soulCallback);
    }

    /**
     * Deletes mentioned chat.
     *
     * @param chatId id of the chat
     * @return {@link SoulResponse} of the {@link Boolean} as observable.
     * It is necessary to call subscribe method of observable to get any result.
     */
    public Observable<SoulResponse<GeneralResponse>> patch(String chatId, String myStatus) {
        return helper.patchChat(chatId, new MyStatus(myStatus), null);
    }

    /**
     * Performs connection to FCM to listen for messages
     */
    public void connectToFCM(String deviceToken) {
     /*   if (deviceToken == null || deviceToken.equals(""))
                throw new Error("Can't establish connection to GCM!");*/

        this.currentUser.updateGCMToken(deviceToken).subscribeOn(Schedulers.io()).subscribe();
        getChatsHelper().connectToGCM(deviceToken);
    }

    /**
     * Helper method, which shows, if SDK is connected to the Firebase Cloud Messaging.
     *
     * @return boolean - if SDK is connected or not
     */
    public boolean isConnectedToFCM() {
        //String representation = SoulStorage.getString(FCM_IS_CONNECTED, "false");
        Log.i(TAG, "isConnectedToFCM: " + false);
        return false;
        //        return false;
    }

    /**
     * Helper method, which shows, if SDK can connect to the Firebase Cloud Messaging.
     *
     * @return boolean - if SDK is able to connect or not.
     */
    public boolean canConnectToFCM() {
        String token = "";
        return currentUser.getCurrentUser() != null && !token.isEmpty();
    }

    /**
     * Loads message history, connects to chat and subscribes to chat, returning messages received.
     * <p><b>Important:</b> this method will also return status messages, like "Sent",
     * "Read", "Delivered" etc.
     * </p>
     *
     * @param chat         the chat to connect and get history
     * @param soulCallback {@link SoulCallback} of the new / unread {@link ChatMessage}s
     */
    public void connectToChat(Chat chat, SoulCallback<ChatMessage> soulCallback) {
        getChatsHelper().connectAndListen(chat).subscribe();
    }

    /**
     * Loads message history, connects to chat and subscribes to chat, returning messages received.
     * <p><b>Important:</b> this method will also return status messages, like "Sent",
     * "Read", "Delivered" etc.
     * </p>
     *
     * @param chat the chat to connect and get history
     * @return {@link SoulResponse} of the new / unread {@link ChatMessage}s as Observable.
     * <p>It is necessary to call subscribe method of observable to get any result.</p>
     */
    public Observable<SoulResponse<ChatMessage>> connectToChat(Chat chat) {
        return getChatsHelper().connectAndListen(chat)
                .subscribeOn(Schedulers.io());
    }

    /**
     * Loads message history, connects to chat and subscribes to chat, returning messages received.
     * <p><b>Important:</b> this method will also return status messages, like "Sent",
     * "Read", "Delivered" etc.
     * </p>
     *
     * @param channelName the chat name to connect and get history
     * @param partners    list of the partners names; needed to send push messages
     * @return {@link SoulResponse} of the new / unread {@link ChatMessage}s as Observable.
     * <p>It is necessary to call subscribe method of observable to get any result.</p>
     */
    public Observable<SoulResponse<ChatMessage>> connectToChat(String channelName, List<String> partners) {
        return getChatsHelper().connectAndListen(channelName, partners)
                .subscribeOn(Schedulers.io());
    }

    // TODO: add callback here

    /**
     * Use this method to send prepared ChatMessage. Note, that server time and sender will be updated automatically.
     *
     * @param channelName channel you wish to send message to.
     * @param message     the message itself.
     */
    public ChatMessage sendMessage(String channelName, ChatMessage message) {
        return getChatsHelper().sendMessage(channelName, message);
    }

    /**
     * loads message history from the last known position in the chat.
     *
     * @param channelName channel you want to get message history of.
     * @return {@link rx.Observable} of {@link ChatMessage}, grouped as List
     */
    public Observable<List<ChatMessage>> getMessageHistory(String channelName, int count) {
        return getChatsHelper().loadHistory(channelName, count);
    }

    /**
     * loads message history from the beginning of the chat, if fromBeginning=true.
     *
     * @param channelName channel you want to get message history of.
     * @return {@link rx.Observable} of {@link ChatMessage}, grouped as List
     */
    public Observable<List<ChatMessage>> getMessageHistory(String channelName, int count, boolean fromBeginning) {
        return getChatsHelper().loadHistory(channelName, count, fromBeginning);
    }

    /**
     * Sends a service notification in chat that message ​<b>deliveredMessage</b>​ was delivered to you.
     * Should be called when user receives the message.
     *
     * @param channelName      channel to send notification
     * @param deliveredMessage {@link ChatMessage} you want to mark as delivered
     */
    public void markMessageDelivered(String channelName, ChatMessage deliveredMessage) {
        // chats.
    }

    /**
     * Sends a service notification in chat that channel was read by you.
     * Should be called when user opens the chat and views messages there.
     *
     * @param channelName channel to send notification
     * @param readMessage {@link ChatMessage} you want to mark as read
     */
    public void markChannelAsRead(String channelName, ChatMessage readMessage) {
        try {
            double time = SoulSystem.getServerTime();
            ChatReadMessage msg = new ChatReadMessage(readMessage, time);
            getChatsHelper().sendMessage(channelName, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unsubscribe current user from future messages retrieval on selected chat
     *
     * @param channelName name of the channel you want to unsubscribe
     */
    public void disconnect(String channelName) {
        getChatsHelper().unsubscribe(channelName);
    }
}
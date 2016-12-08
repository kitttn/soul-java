package io.soulsdk.sdk.helpers.chat;

import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import rx.functions.Action2;

/**
 * @author kitttn
 */
public class PubnubCallback extends SubscribeCallback {
    private Action2<PubNub, PNMessageResult> msgAction;
    public PubnubCallback(Action2<PubNub, PNMessageResult> newMessageAction) {
        this.msgAction = newMessageAction;
    }
    @Override
    public void status(PubNub pubnub, PNStatus status) {
    }

    @Override
    public void message(PubNub pubnub, PNMessageResult message) {
        msgAction.call(pubnub, message);
    }

    @Override
    public void presence(PubNub pubnub, PNPresenceEventResult presence) {

    }
}

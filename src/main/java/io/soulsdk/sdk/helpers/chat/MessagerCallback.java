package io.soulsdk.sdk.helpers.chat;

import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNStatus;

import rx.functions.Action2;

/**
 * @author kitttn
 */
public class MessagerCallback<T> extends PNCallback<T> {
    private Action2<T, PNStatus> historyAction;
    public MessagerCallback(Action2<T, PNStatus> action) {
        this.historyAction = action;
    }
    @Override
    public void onResponse(T result, PNStatus status) {
        historyAction.call(result, status);
    }
}

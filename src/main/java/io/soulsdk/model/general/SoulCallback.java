package io.soulsdk.model.general;


import retrofit2.Response;

/**
 * General Soul Callback
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public interface SoulCallback<T> {

    void onSuccess(Response<T> responseEntity);

    void onError(SoulError error);
}

package io.soulsdk.sdk;

import io.soulsdk.model.general.GeneralResponse;
import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.responses.AuthorizationResponse;
import io.soulsdk.model.responses.CurrentUserRESP;
import io.soulsdk.model.responses.PhoneRequestRESP;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import rx.Observable;

/**
 * <p>
 * Provides a list of methods for User's authorization within different sources. After successful
 * login some methods provide {@link AuthorizationResponse} object that contain {@link CurrentUserRESP}.
 * </p>
 * <p>
 * It is not necessary to get and use this object because all information about authorized user is saved inside the SDK
 * and is persistent available for updating and getting all you need using {@link SoulCurrentUser}.
 * </p>
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class SoulAuth {

    private ServerAPIHelper helper;

    public SoulAuth(ServerAPIHelper hp) {
        helper = hp;
    }
/*
    static void initialize(ServerAPIHelper hp) {
        helper = hp;
    }*/

    public Observable<SoulResponse<AuthorizationResponse>> registerWithPass(String login, String pass) {
        return helper.registerWithPass(login, pass, null);
    }

    public void registerWithPass(String login, String pass, SoulCallback<AuthorizationResponse> soulCallback) {
        helper.registerWithPass(login, pass, soulCallback);
    }

    public Observable<SoulResponse<AuthorizationResponse>> loginWithPass(String login, String pass) {
        return helper.loginWithPass(login, pass, null);
    }

    public void loginWithPass(String login, String pass, SoulCallback<AuthorizationResponse> soulCallback) {
        helper.loginWithPass(login, pass, soulCallback);
    }


    /**
     * Triggers Sever to send SMS with verification code to phone number provided as parameter.
     *
     * @param phoneNumber the phone number
     * @return {@link SoulResponse} of the {@link PhoneRequestRESP} as observable.
     * It is necessary to call subscribe method of observable to get any result.
     */
    public Observable<SoulResponse<PhoneRequestRESP>> requestPhone(String phoneNumber, String method) {
        return helper.requestPhone(phoneNumber, method, null);
    }

    /**
     * Triggers Sever to send SMS with verification code to phone number provided as parameter.
     *
     * @param phoneNumber  the phone number
     * @param soulCallback general {@link SoulCallback} of the {@link PhoneRequestRESP}
     */
    public void requestPhone(String phoneNumber, String method, SoulCallback<PhoneRequestRESP> soulCallback) {
        helper.requestPhone(phoneNumber, method, soulCallback);
    }

    /**
     * Phone number verification by providing verification code from the text of SMS received from Server after
     * call {@link SoulAuth#requestPhone(String, String)} or {@link SoulAuth#requestPhone(String, String, SoulCallback)}
     *
     * @param verificationCode verification code from the text of SMS
     * @return {@link SoulResponse} of the {@link AuthorizationResponse} as observable.
     * It is necessary to call subscribe method of observable to get any result.
     */
    public Observable<SoulResponse<AuthorizationResponse>> verifyPhone(String verificationCode, String verificationMethod) {
        return helper.verifyPhone(verificationCode, verificationMethod, null);
    }

    /**
     * Phone number verification by providing verification code from the text of SMS received from Server after
     * call {@link SoulAuth#requestPhone(String, String)} or {@link SoulAuth#requestPhone(String, String, SoulCallback)}
     *
     * @param verificationCode verification code from the text of SMS
     * @param soulCallback     general {@link SoulCallback} of the {@link AuthorizationResponse}
     */
    public void verifyPhone(String verificationCode, String verificationMethod, SoulCallback<AuthorizationResponse> soulCallback) {
        helper.verifyPhone(verificationCode, verificationMethod, soulCallback);
    }

    /**
     * Will check all credentials the application needed for successful login and make login-HTTP-request to Server.
     * It will try to login with a credentials of the last successful authorization source
     * (via phone verification, Facebook or Google tokens). If there is no or not enough credentials
     * for successful login method will return {@link SoulResponse} with the corresponding error.
     *
     * @return {@link SoulResponse} of the {@link AuthorizationResponse} as observable.
     * It is necessary to call subscribe method of observable to get any result.
     */
    public Observable<SoulResponse<AuthorizationResponse>> login() {
        return helper.login(null);
    }

    /**
     * Will check all credentials the application needed for successful login and make login-HTTP-request to Server.
     * It will try to login with a credentials of the last successful authorization source
     * (via phone verification, Facebook or Google tokens). If there is no or not enough credentials
     * for successful login method will return {@link SoulResponse} with the corresponding error.
     *
     * @param soulCallback general {@link SoulCallback} of the {@link AuthorizationResponse}
     */
    public void login(SoulCallback<AuthorizationResponse> soulCallback) {
        helper.login(soulCallback);
    }

    /**
     * Send logout HTTP-request to Server. Nullifies User's sessionToken on Server. Clear login
     * credentials needed for successful login. User has to be authorized again after this.
     *
     * @param full If set to true, logout will wipe all session data, so the user will have to
     *             re-authenticate, e.g. verify his or her phone number once again.
     * @return {@link SoulResponse} of the Boolean as observable.
     * It is necessary to call subscribe method of observable to get any result.
     */
    public Observable<SoulResponse<GeneralResponse>> logout(boolean full) {
        return helper.logout(full, null);
    }

    /**
     * Send logout HTTP-request to Server. Nullifies User's sessionToken on Server. Clear login
     * credentials needed for successful login. User has to be authorized again after this.
     *
     * @param full         If set to true, logout will wipe all session data, so the user will have to
     *                     re-authenticate, e.g. verify his or her phone number once again.
     * @param soulCallback general {@link SoulCallback} of the Boolean
     */
    public void logout(boolean full, SoulCallback<GeneralResponse> soulCallback) {
        helper.logout(full, soulCallback);
    }
}
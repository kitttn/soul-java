package io.soulsdk.sdk.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;

import io.soulsdk.model.dto.Album;
import io.soulsdk.model.dto.MyStatus;
import io.soulsdk.model.general.GeneralResponse;
import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulError;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.requests.AddReceiptREQ;
import io.soulsdk.model.requests.CreateNewAlbumREQ;
import io.soulsdk.model.requests.PassLoginRegisterREQ;
import io.soulsdk.model.requests.PatchAlbumREQ;
import io.soulsdk.model.requests.PatchPhotoREQ;
import io.soulsdk.model.requests.PatchUserREQ;
import io.soulsdk.model.requests.PhoneRequestREQ;
import io.soulsdk.model.requests.PhoneVerifyLoginREQ;
import io.soulsdk.model.requests.ReactionREQ;
import io.soulsdk.model.requests.ReportUserREQ;
import io.soulsdk.model.responses.AlbumRESP;
import io.soulsdk.model.responses.AlbumsRESP;
import io.soulsdk.model.responses.AuthorizationResponse;
import io.soulsdk.model.responses.ChatRESP;
import io.soulsdk.model.responses.ChatsRESP;
import io.soulsdk.model.responses.CurrentUserRESP;
import io.soulsdk.model.responses.EventsRESP;
import io.soulsdk.model.responses.PhoneRequestRESP;
import io.soulsdk.model.responses.PhotoRESP;
import io.soulsdk.model.responses.PurchaseDoneRESP;
import io.soulsdk.model.responses.PurchasesAvailableRESP;
import io.soulsdk.model.responses.RecommendFiltersRESP;
import io.soulsdk.model.responses.SubscriptionsAvailableRESP;
import io.soulsdk.model.responses.UserCustomSearchRESP;
import io.soulsdk.model.responses.UserRESP;
import io.soulsdk.model.responses.UsersSearchRESP;
import io.soulsdk.network.adapter.NetworkAdapter;
import io.soulsdk.network.adapter.RetrofitAdapter;
import io.soulsdk.network.executor.RequestExecutor;
import io.soulsdk.network.managers.Credentials;
import io.soulsdk.sdk.SoulAuth;
import io.soulsdk.util.Constants;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;


/**
 * Created by Buiarov on 09/03/16.
 */
public class ServerAPIHelper {

    private static final String EMPTY_BODY = "";

    private Credentials credentials;
    private String serverHost;
    private SoulAuth soulAuth;
    private String apiKey;

    public ServerAPIHelper(String serverHost, String apiKey, Credentials credentials) {
        this.credentials = credentials;
        this.apiKey = apiKey;
        this.serverHost = serverHost;
    }

    public void setSoulAuth(SoulAuth soulAuth) {
        this.soulAuth = soulAuth;
    }

    public Observable<SoulResponse<PhoneRequestRESP>>
    requestPhone(String phoneNumber, String method, SoulCallback<PhoneRequestRESP> soulCallback) {
        SoulResponse<PhoneRequestREQ> phoneRequest = credentials.getPhoneRequestREQ(phoneNumber, method);
        if (phoneRequest.isErrorHappened()) {
            if (soulCallback != null) {
                soulCallback.onError(phoneRequest.getError());
                return null;
            } else {
                return Observable.just(new SoulResponse<PhoneRequestRESP>(phoneRequest.getError()));
            }
        } else {
            return new RequestExecutor<>(soulAuth,
                    getAdapter().getService().requestPhone(phoneRequest.getResponse()),
                    credentials, soulCallback, false)
                    .asObservable();
        }
    }

    public Observable<SoulResponse<AuthorizationResponse>>
    verifyPhone(String verificationCode, String verificationMethod, SoulCallback<AuthorizationResponse> soulCallback) {
        SoulResponse<PhoneVerifyLoginREQ> phoneVerify = credentials.getPhoneVerifyREQ(verificationCode, verificationMethod);
        if (phoneVerify.isErrorHappened()) {
            if (soulCallback != null) {
                soulCallback.onError(phoneVerify.getError());
                return null;
            } else {
                return Observable.just(new SoulResponse<>(phoneVerify.getError()));
            }
        } else {
            return new RequestExecutor<>(soulAuth,
                    getAdapter().getService()
                            .verifyPhone(phoneVerify.getResponse()),
                    credentials, soulCallback, Constants.PHONE_LOGIN_SOURCE)
                    .asObservable();
        }
    }

    public Observable<SoulResponse<AuthorizationResponse>>
    login(SoulCallback<AuthorizationResponse> soulCallback) {
        switch (credentials.getLastSuccessfulLoginSource()) {
            case Constants.PHONE_LOGIN_SOURCE:
                return loginViaPhone(soulCallback);
            case Constants.PASS_LOGIN_SOURCE:
                return loginWithPass(credentials.getTempLogin(), credentials.getTempPass(), soulCallback);
            default:
                if (soulCallback != null) {
                    soulCallback.onError(new SoulError("No login credentials",
                            SoulError.NO_LOGIN_CREDENTIALS));
                    return null;
                } else {
                    return Observable.just(new SoulResponse<AuthorizationResponse>(new SoulError("No login credentials",
                            SoulError.NO_LOGIN_CREDENTIALS)));
                }
        }
    }

    public Observable<SoulResponse<AuthorizationResponse>>
    loginViaPhone(SoulCallback<AuthorizationResponse> soulCallback) {
        SoulResponse<PhoneVerifyLoginREQ> phoneVerify = credentials.getPhoneVerifyREQ();
        if (phoneVerify.isErrorHappened()) {
            return Observable.just(new SoulResponse<AuthorizationResponse>(phoneVerify.getError()));
        } else {
            return new RequestExecutor<>(soulAuth, "loginViaPhone",
                    getAdapter().getService()
                            .loginViaPhone(phoneVerify.getResponse()),
                    credentials, soulCallback, false)
                    .asObservable();
        }
    }

    public Observable<SoulResponse<AuthorizationResponse>>
    registerWithPass(String login, String pass, SoulCallback<AuthorizationResponse> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getService()
                        .passRegister(new PassLoginRegisterREQ(login, pass, apiKey)),
                credentials, soulCallback, Constants.PASS_LOGIN_SOURCE, login, pass)
                .asObservable();
    }

    public Observable<SoulResponse<AuthorizationResponse>>
    loginWithPass(String login, String pass, SoulCallback<AuthorizationResponse> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getService()
                        .passLogin(new PassLoginRegisterREQ(
                                login, pass, apiKey)),
                credentials, soulCallback, Constants.PASS_LOGIN_SOURCE, login, pass)
                .asObservable();
    }

    public Observable<SoulResponse<GeneralResponse>>
    logout(boolean full, SoulCallback<GeneralResponse> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(), EMPTY_BODY, credentials.getSessionToken())
                        .logout(full),
                credentials, soulCallback, false, true)
                .asObservable();
    }

    public Observable<SoulResponse<CurrentUserRESP>>
    getMe(SoulCallback<CurrentUserRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getMe(),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<CurrentUserRESP>>
    patchMe(PatchUserREQ patchUserREQ, SoulCallback<CurrentUserRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth, "patchMe",
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        new Gson().toJson(patchUserREQ),
                        credentials.getSessionToken())
                        .patchMe(patchUserREQ),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<SubscriptionsAvailableRESP>>
    getMySubscriptionAvailable(SoulCallback<SubscriptionsAvailableRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getMySubscriptionsAvailable(),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<PurchasesAvailableRESP>>
    getAllPurchases(SoulCallback<PurchasesAvailableRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getAllPurchases(),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<AlbumsRESP>>
    getMyAlbums(Integer offset, Integer limit, SoulCallback<AlbumsRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getMyAlbums(offset, limit),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<Album>>
    createNewAlbum(CreateNewAlbumREQ createNewAlbumREQ, SoulCallback<Boolean> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        new Gson().toJson(createNewAlbumREQ),
                        credentials.getSessionToken())
                        .createNewAlbum(createNewAlbumREQ),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<AlbumRESP>>
    getPhotosFromMyAlbum(String albumName, Integer offset, Integer limit, SoulCallback<AlbumRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getPhotosFromMyAlbum(albumName, offset, limit),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<PhotoRESP>>
    addPhotoToMyAlbum(String albumName, File photo, SoulCallback<PhotoRESP> soulCallback) {

        RequestBody description =
                RequestBody.create(MediaType.parse("image/png"), photo);
        MultipartBody.Part file =
                MultipartBody.Part.createFormData("photo", "photo", description);

        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .addPhotoToMyAlbum(albumName, file),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<PatchAlbumREQ>>
    patchAlbum(String albumName, PatchAlbumREQ patchAlbumREQ, SoulCallback<PatchAlbumREQ> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        new Gson().toJson(patchAlbumREQ),
                        credentials.getSessionToken())
                        .patchAlbum(albumName, patchAlbumREQ),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<GeneralResponse>>
    deleteAlbum(String albumName, SoulCallback<GeneralResponse> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .deleteAlbum(albumName),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<PhotoRESP>>
    getMyPhoto(String albumName, String photoId, SoulCallback<PhotoRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getMyPhoto(albumName, photoId),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<PhotoRESP>>
    getUsersPhoto(String userId, String albumName, String photoId, SoulCallback<PhotoRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getUsersPhoto(userId, albumName, photoId),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<PhotoRESP>>
    patchMyPhoto(String albumName, String photoId, PatchPhotoREQ patchPhotoREQ, SoulCallback<PhotoRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        new Gson().toJson(patchPhotoREQ),
                        credentials.getSessionToken())
                        .patchMyPhoto(albumName, photoId, patchPhotoREQ),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<PhotoRESP>>
    setPhotoAsMain(String albumName, String photoId, SoulCallback<PhotoRESP> soulCallback) {
        PatchPhotoREQ req = new PatchPhotoREQ();
        req.setMainPhoto(true);
        return patchMyPhoto(albumName, photoId, req, soulCallback);
    }

    public Observable<SoulResponse<GeneralResponse>>
    deleteMyPhoto(String albumName, String photoId, SoulCallback<GeneralResponse> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .deleteMyPhoto(albumName, photoId),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<UsersSearchRESP>>
    getRecommendations(String pageToken, String viewingSession, SoulCallback<UsersSearchRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getRecommendationsSearchResult(pageToken, viewingSession),
                credentials, soulCallback, true)
                .asObservable();
    }

    public Observable<SoulResponse<UserCustomSearchRESP>>
    getNextUsersCustomSearchResult(String filterName, Integer after, Integer limit, Long since, SoulCallback<UserCustomSearchRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getNextUsersCustomSearchResult(filterName, after, limit, since),
                credentials, soulCallback, true)
                .asObservable();
    }

    public Observable<SoulResponse<RecommendFiltersRESP>>
    getRecommendationsFilter(SoulCallback<RecommendFiltersRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getRecommendationsFilter(),
                credentials, soulCallback, true)
                .asObservable();
    }

    public Observable<SoulResponse<RecommendFiltersRESP>>
    patchRecommendationsFilter(JsonObject filters, SoulCallback<RecommendFiltersRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        new Gson().toJson(filters),
                        credentials.getSessionToken())
                        .patchRecommendationsFilter(filters),
                credentials, soulCallback, true)
                .asObservable();
    }

    public Observable<SoulResponse<UserRESP>>
    getUser(String userId, SoulCallback<UserRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getUser(userId),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<UserRESP>>
    sendReactionToUser(String userId, String reactingType, ReactionREQ reactionREQ, SoulCallback<UserRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        new Gson().toJson(reactionREQ),
                        credentials.getSessionToken())
                        .sendReactionToUser(userId, reactingType, reactionREQ),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<GeneralResponse>>
    deleteReactionToUser(String userId, String reactingType, SoulCallback<Boolean> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .deleteReactionToUser(userId, reactingType),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<GeneralResponse>>
    flagUser(String userId, ReportUserREQ reportUserREQ, SoulCallback<GeneralResponse> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        new Gson().toJson(reportUserREQ),
                        credentials.getSessionToken())
                        .flagUser(userId, reportUserREQ),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<GeneralResponse>>
    deleteFlagUser(String userId, SoulCallback<GeneralResponse> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .deleteFlagUser(userId),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<AlbumsRESP>>
    getUsersAlbums(String userId, Integer offset, Integer limit, SoulCallback<AlbumsRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getUsersAlbums(userId, offset, limit),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<AlbumRESP>>
    getUsersPhotosFromAlbum(String userId, String albumName, Integer offset, Integer limit, SoulCallback<AlbumRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getUsersPhotosFromAlbum(userId, albumName, offset, limit),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<ChatsRESP>>
    getAllChats(Integer after, Integer limit, Boolean showExpired, String status, SoulCallback<ChatsRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getAllChats(after, limit, showExpired, status),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<ChatRESP>>
    getOneChat(String chatId, SoulCallback<ChatRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getOneChat(chatId),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<GeneralResponse>>
    patchChat(String chatId, MyStatus myStatus, SoulCallback<GeneralResponse> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        new Gson().toJson(myStatus),
                        credentials.getSessionToken())
                        .patchChat(chatId, myStatus),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<EventsRESP>>
    getEvents(Double since, Double until, Integer limit, Boolean ascending, SoulCallback<EventsRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getEvents(since, until, limit, ascending ? 1 : 0),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<EventsRESP>>
    getEvents(Double since, Integer limit, Boolean ascending, SoulCallback<EventsRESP> soulCallback) {
        double d = since;
        long s = (long) d;
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        EMPTY_BODY, credentials.getSessionToken())
                        .getEvents(s, limit, ascending ? 1 : 0),
                credentials, soulCallback, false)
                .asObservable();
    }

    public Observable<SoulResponse<PurchaseDoneRESP>>
    addReceipt(AddReceiptREQ addReceiptREQ, SoulCallback<PurchaseDoneRESP> soulCallback) {
        return new RequestExecutor<>(soulAuth,
                getAdapter().getSecuredService(
                        credentials.getUserId(),
                        new GsonBuilder().disableHtmlEscaping().create().toJson(addReceiptREQ),
                        credentials.getSessionToken())
                        .addReceipt(addReceiptREQ),
                credentials, soulCallback, false)
                .asObservable();
    }

    public NetworkAdapter getAdapter() {
        return new RetrofitAdapter(serverHost);
    }
}

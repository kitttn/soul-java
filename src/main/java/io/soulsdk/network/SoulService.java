package io.soulsdk.network;

import com.google.gson.JsonObject;

import io.soulsdk.model.dto.Album;
import io.soulsdk.model.dto.MyStatus;
import io.soulsdk.model.general.GeneralResponse;
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
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by buyaroff1 on 19/01/16.
 */
public interface SoulService {
    String API_VERS = "?v=1.0.0";
    // String API_VERS = "";
    String POST_METHOD = "POST";
    String GET_METHOD = "GET";
    String PATCH_METHOD = "PATCH";
    String DELETE_METHOD = "DELETE";
    String PUT_METHOD = "PUT";

    String REGISTER_PASS_REQUEST_URL = "auth/password/register" + API_VERS;
    String LOGIN_PASS_REQUEST_URL = "auth/password/login" + API_VERS;

    String AUTH_PHONE_REQUEST_URL = "auth/phone/request" + API_VERS;
    String AUTH_PHONE_VERIFY_URL = "auth/phone/verify" + API_VERS;
    String AUTH_PHONE_LOGIN_URL = "auth/phone/login" + API_VERS;
    String AUTH_LOGOUT_URL = "auth/logout" + API_VERS;

    String ME_URL = "me" + API_VERS;
    String MY_PRODUCTS_URL = "me/products" + API_VERS;
    String MY_PRODUCTS_SUBSCRIPTIONS_AVAILABLE_URL = "me/products/subscriptions/available" + API_VERS;
    String MY_PRODUCTS_GET_ALL_PURCHASES = "purchases/all" + API_VERS;
    String MY_ALBUMS_URL = "me/albums" + API_VERS;
    String ONE_MY_ALBUM_URL = "me/albums/{albumName}" + API_VERS;
    String ONE_PHOTO_IN_MY_ALBUM_URL = "me/albums/{albumName}/{photoId}" + API_VERS;

    String USERS_RECOMMENDATIONS_URL = "users/recommendations" + API_VERS;
    String USERS_RECOMMENDATIONS_FILTER_URL = "users/recommendations/filter" + API_VERS;
    String USERS_CUSTOM_FILTER_URL = "users/set/{filterName}" + API_VERS;
    String USER_BY_ID_URL = "users/{userId}" + API_VERS;
    String SEND_REACTION_TO_USER_URL = "users/{userId}/reactions/sent/{reactionType}" + API_VERS;
    String FLAG_USER_URL = "users/{userId}/flag" + API_VERS;
    String ALL_USERS_ALBUMS_URL = "users/{userId}/albums" + API_VERS;
    String ONE_USERS_ALBUM_URL = "users/{userId}/albums/{albumName}" + API_VERS;
    String ONE_PHOTO_FROM_ALBUM_URL = "users/{userId}/albums/{albumName}/{photoId}" + API_VERS;

    String ALL_CHATS_URL = "chats" + API_VERS;
    String ONE_CHAT_URL = "chats/{chatId}" + API_VERS;

    String EVENTS_URL = "events" + API_VERS;

    String ADD_GOOGLE_PLAY_RECEIPT_URL = "purchases/order/googleplay" + API_VERS;


    // [ /auth ] - A collection of Authentication (login/registration/password reset, etc.)
    // endpoints. Use only the ones that your application is configured to use.
    // Authentication method return an accessToken that you can use to access secured endpoints.

    @POST(AUTH_PHONE_REQUEST_URL)
    Observable<Response<PhoneRequestRESP>>
    requestPhone(@Body PhoneRequestREQ body);

    @POST(AUTH_PHONE_VERIFY_URL)
    Observable<Response<AuthorizationResponse>>
    verifyPhone(@Body PhoneVerifyLoginREQ body);

    @POST(AUTH_PHONE_LOGIN_URL)
    Observable<Response<AuthorizationResponse>>
    loginViaPhone(@Body PhoneVerifyLoginREQ body);

    @POST(AUTH_LOGOUT_URL)
    Observable<Response<GeneralResponse>>
    logout(@Query("full") Boolean full);

    @POST(REGISTER_PASS_REQUEST_URL)
    Observable<Response<AuthorizationResponse>>
    passRegister(@Body PassLoginRegisterREQ body);

    @POST(LOGIN_PASS_REQUEST_URL)
    Observable<Response<AuthorizationResponse>>
    passLogin(@Body PassLoginRegisterREQ body);

    // ========================================================================================== =)


    // [ /me ] - The endpoint that lets you interact with the currently logged in user

    @GET(ME_URL)
    Observable<Response<CurrentUserRESP>>
    getMe();

    @PATCH(ME_URL)
    Observable<Response<CurrentUserRESP>>
    patchMe(@Body PatchUserREQ patchUserREQ);

    @GET(MY_PRODUCTS_SUBSCRIPTIONS_AVAILABLE_URL)
    Observable<Response<SubscriptionsAvailableRESP>>
    getMySubscriptionsAvailable();

    @GET(MY_PRODUCTS_GET_ALL_PURCHASES)
    Observable<Response<PurchasesAvailableRESP>>
    getAllPurchases();

    @GET(MY_ALBUMS_URL)
    Observable<Response<AlbumsRESP>>
    getMyAlbums(@Query("offset") Integer offset,
                @Query("limit") Integer limit);

    @POST(MY_ALBUMS_URL)
    Observable<Response<Album>>
    createNewAlbum(@Body CreateNewAlbumREQ createNewAlbumREQ);

    @GET(ONE_MY_ALBUM_URL)
    Observable<Response<AlbumRESP>>
    getPhotosFromMyAlbum(@Path("albumName") String albumName,
                         @Query("offset") Integer offset,
                         @Query("limit") Integer limit);

    @POST(ONE_MY_ALBUM_URL)
    @Multipart
    Observable<Response<PhotoRESP>>
    addPhotoToMyAlbum(@Path("albumName") String albumName,
                      @Part MultipartBody.Part file);

    @PATCH(ONE_MY_ALBUM_URL)
    Observable<Response<PatchAlbumREQ>>
    patchAlbum(@Path("albumName") String albumName,
               @Body PatchAlbumREQ patchAlbumREQ);

    @DELETE(ONE_MY_ALBUM_URL)
    Observable<Response<GeneralResponse>>
    deleteAlbum(@Path("albumName") String albumName);

    @GET(ONE_PHOTO_IN_MY_ALBUM_URL)
    Observable<Response<PhotoRESP>>
    getMyPhoto(@Path("albumName") String albumName,
               @Path("photoId") String photoId);

    @PATCH(ONE_PHOTO_IN_MY_ALBUM_URL)
    Observable<Response<PhotoRESP>>
    patchMyPhoto(@Path("albumName") String albumName,
                 @Path("photoId") String photoId,
                 @Body PatchPhotoREQ patchPhotoREQ);

    @DELETE(ONE_PHOTO_IN_MY_ALBUM_URL)
    Observable<Response<GeneralResponse>>
    deleteMyPhoto(@Path("albumName") String albumName,
                  @Path("photoId") String photoId);

    @GET(ONE_PHOTO_FROM_ALBUM_URL)
    Observable<Response<PhotoRESP>>
    getUsersPhoto(@Path("userId") String userId,
                  @Path("albumName") String albumName,
                  @Path("photoId") String photoId);


    // [ /me ]  - search users

    @GET(USERS_RECOMMENDATIONS_URL)
    Observable<Response<UsersSearchRESP>>
    getRecommendationsSearchResult(@Query("uniqueToken") String uniqueToken,
                                   @Query("viewingSession") String viewingSession);

    @GET(USERS_RECOMMENDATIONS_FILTER_URL)
    Observable<Response<RecommendFiltersRESP>>
    getRecommendationsFilter();

    @PATCH(USERS_RECOMMENDATIONS_FILTER_URL)
    Observable<Response<RecommendFiltersRESP>>
    patchRecommendationsFilter(@Body JsonObject filters);

    @GET(USERS_CUSTOM_FILTER_URL)
    Observable<Response<UserCustomSearchRESP>>
    getNextUsersCustomSearchResult(@Path("filterName") String filterName,
                                   @Query("after") Integer after,
                                   @Query("limit") Integer limit,
                                   @Query("since") Long since);

    @GET(USER_BY_ID_URL)
    Observable<Response<UserRESP>>
    getUser(@Path("userId") String userId);

    @POST(SEND_REACTION_TO_USER_URL)
    Observable<Response<UserRESP>>
    sendReactionToUser(@Path("userId") String userId,
                       @Path("reactionType") String reactingType,
                       @Body ReactionREQ reactionREQ);

    @DELETE(SEND_REACTION_TO_USER_URL)
    Observable<Response<GeneralResponse>>
    deleteReactionToUser(@Path("userId") String userId,
                         @Path("reactionType") String reactingType);

    @POST(FLAG_USER_URL)
    Observable<Response<GeneralResponse>>
    flagUser(@Path("userId") String userId,
             @Body ReportUserREQ reportUserREQ);

    @POST(FLAG_USER_URL)
    Observable<Response<GeneralResponse>>
    deleteFlagUser(@Path("userId") String userId);

    @GET(ALL_USERS_ALBUMS_URL)
    Observable<Response<AlbumsRESP>>
    getUsersAlbums(@Path("userId") String userId,
                   @Query("offset") Integer offset,
                   @Query("limit") Integer limit);

    @GET(ONE_USERS_ALBUM_URL)
    Observable<Response<AlbumRESP>>
    getUsersPhotosFromAlbum(@Path("userId") String userId,
                            @Path("albumName") String albumName,
                            @Query("offset") Integer offset,
                            @Query("limit") Integer limit);

    // [ /chats ]

    @GET(ALL_CHATS_URL)
    Observable<Response<ChatsRESP>>
    getAllChats(@Query("after") Integer after,
                @Query("limit") Integer limit,
                @Query("showExpired") Boolean showExpired,
                @Query("myStatus") String myStatus);

    @GET(ONE_CHAT_URL)
    Observable<Response<ChatRESP>>
    getOneChat(@Path("chatId") String chatId);

/*    @DELETE(ONE_CHAT_URL)
    Observable<Boolean>
    patchChat(@Path("chatId") String chatId);*/

    @PATCH(ONE_CHAT_URL)
    Observable<Response<GeneralResponse>>
    patchChat(@Path("chatId") String chatId, @Body MyStatus status);

    // [ /events ]

    @GET(EVENTS_URL)
    Observable<Response<EventsRESP>>
    getEvents(@Query("since") Long since,
              @Query("limit") Integer limit,
              @Query("ascending") Integer ascending);

    @GET(EVENTS_URL)
    Observable<Response<EventsRESP>>
    getEvents(@Query("until") Number until,
              @Query("limit") Integer limit,
              @Query("ascending") Integer ascending);

    @GET(EVENTS_URL)
    Observable<Response<EventsRESP>>
    getEvents(@Query("since") Double since,
              @Query("until") Double until,
              @Query("limit") Integer limit,
              @Query("ascending") Integer ascending);

    // [ /purchases ]

    @POST(ADD_GOOGLE_PLAY_RECEIPT_URL)
    Observable<Response<PurchaseDoneRESP>>
    addReceipt(@Body AddReceiptREQ addReceiptREQ);

}

package io.soulsdk.sdk;

import log.Log;

import io.soulsdk.model.dto.Filterable;
import io.soulsdk.model.dto.NotificationTokens;
import io.soulsdk.model.dto.User;
import io.soulsdk.model.dto.UsersLocation;
import io.soulsdk.model.dto.UsersParameters;
import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.requests.PatchUserREQ;
import io.soulsdk.model.responses.CurrentUserRESP;
import io.soulsdk.model.tasks.GeneralTask;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import io.soulsdk.util.SoulVariableUtil;
import io.soulsdk.util.TimeUtils;
import io.soulsdk.util.storage.TmpStorage;
import rx.Observable;
import rx.functions.Action1;

import static io.soulsdk.util.Constants.F_LOOKING_FOR;

/**
 * <p>
 * Provides a list of methods for working with CurrentUser - user successfully authorized via
 * Soul API or this SDK. Some methods translate API responses and some are more convenient to use
 * as a quick access to Current User's properties or updating it.
 * It is not necessary to work with Server's response object {@link CurrentUserRESP} or
 * {@link io.soulsdk.model.responses.AuthorizationResponse}.
 * </p>
 * <p>
 * Authorized User is always cached after successful authorization and his properties will be
 * always updated on server-side after updating (patching) some property using
 * methods of this class.
 * </p>
 * <b>Important:</b> User object is not persistent. Use methods of this class to update some of Current
 * User's properties.
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class SoulCurrentUser {

    private final String TAG = "SoulCurrentUser";

    private ServerAPIHelper helper;
    private SoulConfigs configs;

    public SoulCurrentUser(SoulConfigs configs, ServerAPIHelper hp) {
        this.configs = configs;
        helper = hp;
    }


    /**
     * Initialize.
     *
     * @param hp the hp
     */
  /*  void initialize(ServerAPIHelper hp) {
        helper = hp;
    }*/


    //==========================        User        ================================================


    /**
     * Return current user that was successfully authorized and cached.
     *
     * @return current user object
     */
    public User getCurrentUser() {
        User user = TmpStorage.getCurrentUser();
        if (user == null) {
            Log.e(TAG, "SoulCurrentUser: CurrentUser is not cached");
        }
        return user;
    }

    /**
     * Patch user eventually.
     * <p>
     * This method will try to patch User object. Data should be uploaded automatically but there is
     * no specific time when this will happen. If HTTP-request will failed or the internet
     * connection is not available this method will schedule background task to send it later when
     * connection will be restored and will try to send data to server until success even if user
     * stopped and relaunched the application.
     * <p>
     * Important: Only GCM token and UserParameters will updated
     *
     * @param user User object
     */
    public void patchUserEventually(User user) {
        patchUserEventually(createPatchUserREQ(user));
    }

    /**
     * Patch current user. It is not necessary to use {@link CurrentUserRESP} from this response, it is more
     * convenient to use methods of this class instead.
     * <p>
     * Important: Only GCM token and UserParameters will updated
     *
     * @param user         User object
     * @param soulCallback general {@link SoulCallback} of {@link CurrentUserRESP}
     */
    public void patchCurrentUser(User user, SoulCallback<CurrentUserRESP> soulCallback) {
        helper.patchMe(createPatchUserREQ(user), soulCallback);
    }

    /**
     * Patch current user. It is not necessary to use {@link CurrentUserRESP} from this response, it is more
     * convenient to use methods of this class instead.
     * <p>
     * Important: Only GCM token and UserParameters will updated
     *
     * @param user User object
     * @return observable of general {@link SoulResponse} of {@link CurrentUserRESP}
     */
    public Observable<SoulResponse<CurrentUserRESP>> patchCurrentUser(User user) {
        return helper.patchMe(createPatchUserREQ(user), null);
    }


    //==========================        Lifetime        ============================================


    /**
     * Provides time left to the end of possibility to search other users and be able to be seen by
     * other users.
     *
     * @return the time left in milliseconds
     */
    public long getTimeLeft() {
        User user = getCachedUser();
        if (user == null) return -1;
        long timeLeft = -1;
        try {
            long usersAvailableTill = SoulVariableUtil.getUsersAvailableTill(user);
            Log.e(TAG, "usersAvailableTill TimeLeft: " + usersAvailableTill);
            if (usersAvailableTill == -1) return -1;
            timeLeft = TimeUtils.formatTimeFromAPI(usersAvailableTill) - SoulSystem.getServerTime();
        } catch (Exception e) {
            Log.e(TAG, "getTimeLeft(): ", e);
        }
        return timeLeft;
    }

    /**
     * Makes Current User be able to search other users and be seen by other users during
     * "Search Lifetime" period that was sat in {@link SoulConfigs#setSearchLifeTimeValue(long)}
     *
     * @return observable of general {@link SoulResponse} of {@link CurrentUserRESP}
     */
    public Observable<SoulResponse<CurrentUserRESP>> turnSearchOn() {
        return patchCurrentUserOnServer(patchAvailableTillOn());
    }

    /**
     * Makes Current User be able to search other users and be seen by other users during
     * "Search Lifetime" period that was sat in {@link SoulConfigs#setSearchLifeTimeValue(long)}
     *
     * @param soulCallback general {@link SoulCallback} of {@link CurrentUserRESP}
     */
    public void turnSearchOn(SoulCallback<CurrentUserRESP> soulCallback) {
        patchCurrentUserOnServer(patchAvailableTillOn(), soulCallback);
    }
/*
    public void turnSearchOnWithLocation(SoulCallback<CurrentUserRESP> soulCallback) {
        Location location = TmpStorage.getLocation();
        if (location != null) {
            patchCurrentUserOnServer(patchAvailableTillOnWithLocation(location), soulCallback);
        } else {
            Log.e(TAG, "Can't patch availability On - there is no local location");
        }
    }*/

    /**
     * Makes Current User not be able to search other users and not be seen by other users
     *
     * @return observable of general {@link SoulResponse} of {@link CurrentUserRESP}
     */
    public Observable<SoulResponse<CurrentUserRESP>> turnSearchOff() {
        resetLastLocationUpdateTime();
        return patchCurrentUserOnServer(patchAvailableTillOff());
    }

    /**
     * Makes Current User not be able to search other users and not be seen by other users
     *
     * @param soulCallback general {@link SoulCallback} of {@link CurrentUserRESP}
     */
    public void turnSearchOff(SoulCallback<CurrentUserRESP> soulCallback) {
        patchCurrentUserOnServer(patchAvailableTillOff(), soulCallback);
    }


    //==========================          GCM Token         ========================================

    /**
     * Returns current Google Cloud Messaging token saved for Current User before. This value is
     * just cached token. There is no guarantee it is valid, but it is always possible to update it.
     *
     * @return gcm token
     */
    public String getGCMToken() {
        User user = getCachedUser();
        if (user == null) return null;
        String token = null;
        try {
            token = user.getNotificationTokens().getGCM();
        } catch (Exception e) {
            Log.e(TAG, "getGCMToken(): ", e);
        }
        return token;
    }

    /**
     * Updates gcm token that was gotten from [gms:play-services].
     *
     * @param token        the token
     * @param soulCallback general {@link SoulCallback} of {@link CurrentUserRESP}
     */
    public void updateGCMToken(String token, SoulCallback<CurrentUserRESP> soulCallback) {
        patchCurrentUserOnServer(patchGCMToken(token), soulCallback);
    }

    /**
     * Updates gcm token that was gotten from [gms:play-services].
     *
     * @param token the token
     * @return observable of general {@link SoulResponse} of {@link CurrentUserRESP}
     */
    public Observable<SoulResponse<CurrentUserRESP>> updateGCMToken(String token) {
        return patchCurrentUserOnServer(patchGCMToken(token));
    }


    //==========================          My Gender         ========================================

    /**
     * Returns gender of Current User.
     *
     * @return gender of Current User
     */
    public String getSelfGender() {
        User user = getCachedUser();
        if (user == null) return null;
        String gender = null;
        try {
            gender = SoulVariableUtil.getUsersGender(user);
        } catch (Exception e) {
            Log.e(TAG, "getGCMToken(): ", e);
        }
        return gender;
    }

    /**
     * Updates gender of Current User.
     *
     * @param gender       gender string value
     * @param soulCallback general {@link SoulCallback} of {@link CurrentUserRESP}
     */
    public void updateSelfGender(String gender, SoulCallback<CurrentUserRESP> soulCallback) {
        patchCurrentUserOnServer(patchSelfGender(gender), soulCallback);
    }

    /**
     * Updates gender of Current User.
     *
     * @param gender gender string value
     * @return observable of general {@link SoulResponse} of {@link CurrentUserRESP}
     */
    public Observable<SoulResponse<CurrentUserRESP>> updateSelfGender(String gender) {
        return patchCurrentUserOnServer(patchSelfGender(gender));
    }

    public Observable<SoulResponse<CurrentUserRESP>> updateGenderCombo(String selfGender, String targetGender) {
        return patchCurrentUserOnServer(patchComboGender(selfGender, targetGender));
    }

    //==========================          Target Gender         ========================================


    /**
     * Gets gender of users who Current User is looking for. Only users with this gender set will be
     * retrieved in users search result,for example: {@link SoulUsers#getNextSearchResult(String, String)}.
     *
     * @return the target gender string value
     */
    public String getTargetGender() {
        User user = getCachedUser();
        if (user == null) return null;
        String gender = null;
        try {
            gender = SoulVariableUtil.getUsersTargetGender(user);
        } catch (Exception e) {
            Log.e(TAG, "getTargetGender(): ", e);
        }
        return gender;
    }

    /**
     * Updates gender of users who Current User is looking for. Only users with this gender set will be
     * retrieved in users search result,for example: {@link SoulUsers#getNextSearchResult(String, String)}.
     *
     * @param gender       gender string value
     * @param soulCallback general {@link SoulCallback} of {@link CurrentUserRESP}
     */
    public void updateTargetGender(String gender, SoulCallback<CurrentUserRESP> soulCallback) {
        patchCurrentUserOnServer(patchTargetGender(gender), soulCallback);
    }

    /**
     * Updates gender of users who Current User is looking for. Only users with this gender set will be
     * retrieved in users search result,for example: {@link SoulUsers#getNextSearchResult(String, String)}.
     *
     * @param gender gender string value
     * @return observable of general {@link SoulResponse} of {@link CurrentUserRESP}
     */
    public Observable<SoulResponse<CurrentUserRESP>> updateTargetGender(String gender) {
        updateTargetGenderLocally(gender);
        return patchCurrentUserOnServer(patchTargetGender(gender));
    }


    //==========================       Location          ===========================================


    /**
     * Returns lats updated location of Current User. The object {@link UsersLocation} contains
     * property {updatedTime} according server-time.
     *
     * @return user's location
     */

    public UsersLocation getLastLocalLocation() {
        /*Location location = TmpStorage.getLocation();
        if (location == null) return null;
        return new UsersLocation(location.getLatitude(), location.getLongitude());*/
        return new UsersLocation();
    }

    /**
     * Updates Current User's location
     *
     * @param location location
     * @return observable of general {@link SoulResponse} of {@link CurrentUserRESP}
     */

    /*public void updateLocationLocally(Location location) {
        TmpStorage.setLocation(location);
        LogUtils.updateLocation(location);
    }

    *//**
     * Updates Current User's location
     *
     *//*
    public void updateLocation(Location location, SoulCallback<CurrentUserRESP> soulCallback) {
        patchCurrentUserOnServer(createPatchUserREQ(location), soulCallback);
    }*/

    public void resetLastLocationUpdateTime() {

    }


    //========================    Update Parameters      =======================================


    public void updateUserParameters(UsersParameters parameters, SoulCallback<CurrentUserRESP> soulCallback) {
        patchCurrentUserOnServer(patchUserParameters(parameters), soulCallback);
    }

    public Observable<SoulResponse<CurrentUserRESP>> updateUserParameters(UsersParameters parameters) {
        return patchCurrentUserOnServer(patchUserParameters(parameters));
    }

    //========================     Default API Requests      =======================================


    /**
     * Returns {@link CurrentUserRESP} directly from server. But Current User will be cached in any
     * case.
     *
     * @param soulCallback general {@link SoulCallback} of {@link CurrentUserRESP}
     */
    public void getMeFromServer(SoulCallback<CurrentUserRESP> soulCallback) {
        helper.getMe(soulCallback);
    }

    /**
     * Returns {@link CurrentUserRESP} directly from server. But Current User will be cached in any
     * case.
     *
     * @return observable of general {@link SoulResponse} of {@link CurrentUserRESP}
     */
    public Observable<SoulResponse<CurrentUserRESP>> getMeFromServer() {
        return helper.getMe(null);
    }

    /**
     * Patch current user. It is more convenient to use {@link #patchCurrentUser(User, SoulCallback)}
     * instead.
     * <p>
     * Important: Only GCM token and UserParameters will updated
     *
     * @param patchUserREQ PatchUserREQ object
     * @param soulCallback general {@link SoulCallback} of {@link CurrentUserRESP}
     */
    public void patchCurrentUserOnServer(PatchUserREQ patchUserREQ, SoulCallback<CurrentUserRESP> soulCallback) {
        helper.patchMe(patchUserREQ, soulCallback);
    }

    /**
     * Patch current user. It is more convenient to use {@link #patchCurrentUser(User)}
     * instead.
     * <p>
     * Important: Only GCM token and UserParameters will updated
     *
     * @param patchUserREQ the patch user req
     * @return the observable
     */
    public Observable<SoulResponse<CurrentUserRESP>> patchCurrentUserOnServer(PatchUserREQ patchUserREQ) {
        return helper.patchMe(patchUserREQ, null);
    }


    //============================     Private Utils Methods      ===========================================


    private User getCachedUser() {
        User user = TmpStorage.getCurrentUser();
        if (user == null) {
            Log.e(TAG, "getLastLocation(): ", new Throwable("SoulCurrentUser: CurrentUser is not cached. " +
                    "Please authorize."));
            return null;
        }
        return user;
    }

    private void patchUserEventually(final PatchUserREQ patchUserREQ) {
        patchCurrentUserOnServer(patchUserREQ).subscribe(
                new Action1<SoulResponse<CurrentUserRESP>>() {
                    @Override
                    public void call(SoulResponse<CurrentUserRESP> res) {
                        parseEventualResponse(res, null);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable e) {
                        handleErrorTask(e, patchUserREQ);
                    }
                });
    }

    private PatchUserREQ createPatchUserREQ(User user) {
        PatchUserREQ patchUserREQ = new PatchUserREQ();
        patchUserREQ.setParameters(user.getParameters());
        patchUserREQ.setNotificationTokens(user.getNotificationTokens());
        return patchUserREQ;
    }
/*
    private PatchUserREQ createPatchUserREQ(Location location) {
        PatchUserREQ patchUserREQ = new PatchUserREQ();
        Filterable filterable = new Filterable();
        filterable.setLocation(new UsersLocation(location.getLatitude(), location.getLongitude()));
        UsersParameters usersParameters = new UsersParameters();
        usersParameters.setFilterable(filterable);
        patchUserREQ.setParameters(usersParameters);
        return patchUserREQ;
    }*/

    private PatchUserREQ patchAvailableTillOn() {
        long s = SoulSystem.getServerTime() / 1000;
        long r = configs.getSearchLifeTimeValue();
        long rr = s + r;
        Log.e(TAG, " TimeLeft:  + SoulSystem.getServerTime() / 1000: " + s);
        Log.e(TAG, " TimeLeft:  + SoulConfigs.getSearchLifeTimeValue(): " + r);
        Log.e(TAG, " TimeLeft:  + patchAvailableTillOn: " + rr);
        return patchAvailableTill(rr);
    }

    private PatchUserREQ patchAvailableTillOff() {
        return patchAvailableTill(-1);
    }
/*
    private PatchUserREQ patchAvailableTillOnWithLocation(Location location) {
        long s = SoulSystem.getServerTime() / 1000;
        long r = configs.getSearchLifeTimeValue();
        long availableTill = s + r;
        Log.e(TAG, " TimeLeft:  + SoulSystem.getServerTime() / 1000: " + s);
        Log.e(TAG, " TimeLeft:  + SoulConfigs.getSearchLifeTimeValue(): " + r);
        Log.e(TAG, " TimeLeft:  + patchAvailableTillOn: " + availableTill);
        PatchUserREQ patchUserREQ = new PatchUserREQ();
        Filterable filterable = new Filterable();
        filterable.setAvailableTill(availableTill);
        filterable.setLocation(new UsersLocation(location.getLatitude(), location.getLongitude()));
        UsersParameters usersParameters = new UsersParameters();
        usersParameters.setFilterable(filterable);
        patchUserREQ.setParameters(usersParameters);
        return patchUserREQ;
    }*/

    private PatchUserREQ patchAvailableTill(long availableTill) {
        PatchUserREQ patchUserREQ = new PatchUserREQ();
        Filterable filterable = new Filterable();
        filterable.setAvailableTill(availableTill);
        UsersParameters usersParameters = new UsersParameters();
        usersParameters.setFilterable(filterable);
        patchUserREQ.setParameters(usersParameters);
        return patchUserREQ;
    }

    private PatchUserREQ patchGCMToken(String token) {
        PatchUserREQ patchUserREQ = new PatchUserREQ();
        NotificationTokens notificationTokens = new NotificationTokens();
        notificationTokens.setGCM(token);
        patchUserREQ.setNotificationTokens(notificationTokens);
        return patchUserREQ;
    }

    private PatchUserREQ patchSelfGender(String gender) {
        PatchUserREQ patchUserREQ = new PatchUserREQ();
        UsersParameters usersParameters = new UsersParameters();
        Filterable filterable = new Filterable();
        filterable.setGender(gender);
        usersParameters.setFilterable(filterable);
        patchUserREQ.setParameters(usersParameters);
        return patchUserREQ;
    }

    private PatchUserREQ patchTargetGender(String gender) {
        PatchUserREQ patchUserREQ = new PatchUserREQ();
        UsersParameters usersParameters = new UsersParameters();
        Filterable filterable = new Filterable();
        filterable.setLookingForGender(gender);
        usersParameters.setFilterable(filterable);
        patchUserREQ.setParameters(usersParameters);
        return patchUserREQ;
    }

    private PatchUserREQ patchComboGender(String selfGender, String targetGender) {
        PatchUserREQ patchUserREQ = new PatchUserREQ();
        UsersParameters usersParameters = new UsersParameters();
        Filterable filterable = new Filterable();
        filterable.setGender(selfGender);
        filterable.setLookingForGender(targetGender);
        usersParameters.setFilterable(filterable);
        patchUserREQ.setParameters(usersParameters);
        return patchUserREQ;
    }


    private PatchUserREQ patchUserParameters(UsersParameters params) {
        PatchUserREQ patchUserREQ = new PatchUserREQ();
        patchUserREQ.setParameters(params);
        return patchUserREQ;
    }

    private void parseEventualResponse(SoulResponse response, GeneralTask task) {
        //TODO parse response
        if (response != null && response.getError() != null)
            Log.e(TAG, "parseEventualResponse: " + response.getError().getCode() + " : "
                    + response.getError().getDescription());
    }

    private void handleErrorTask(Throwable e, PatchUserREQ req) {
        //TODO make future task to save eventually
        Log.e(TAG, "handleErrorTask: " + e.getMessage());
    }

    private void updateTargetGenderLocally(String gender) {
        User me = getCurrentUser();
        me.getParameters().getFilterable().setProperty(F_LOOKING_FOR, gender);
        TmpStorage.setCurrentUser(me);

    }

}

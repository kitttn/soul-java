package io.soulsdk.network.executor;

import io.soulsdk.model.general.GeneralResponse;
import io.soulsdk.model.responses.AuthorizationResponse;
import io.soulsdk.model.responses.CurrentUserRESP;
import io.soulsdk.model.responses.EventsRESP;
import io.soulsdk.model.responses.UsersSearchRESP;
import io.soulsdk.network.managers.Credentials;
import io.soulsdk.network.managers.EventManager;
import io.soulsdk.util.Constants;
import io.soulsdk.util.TimeUtils;
import io.soulsdk.util.storage.TmpStorage;
import log.Log;
import rx.Observable;

/**
 * Created by Buiarov on 24/03/16.
 */
public class ResponseParser<T> {

    private static final String TAG = "ResponseParser";
    private static final String TEMP_LOGIN = "TMP_LOGIN";
    private static final String TEMP_PASS = "TMP_PASS";

    private Credentials credentials;
    private String authSource;
    private boolean isSearch;
    private boolean isLogout = false;
    private String tempLogin;
    private String tempPass;

    public ResponseParser(Credentials credentials, String authSource, boolean isSearch) {
        this.credentials = credentials;
        this.authSource = authSource;
        this.isSearch = isSearch;
    }

    public ResponseParser(Credentials credentials, String authSource, boolean isSearch, boolean isLogout) {
        this.credentials = credentials;
        this.authSource = authSource;
        this.isSearch = isSearch;
        this.isLogout = isLogout;
        parseLogout();
    }

    public ResponseParser(Credentials credentials, String authSource, boolean isLogout,
                          String tempLogin, String tempPass) {
        this.credentials = credentials;
        this.authSource = authSource;
        this.isLogout = isLogout;
        this.tempLogin = tempLogin;
        this.tempPass = tempPass;
        parseLogout();
    }

    public void parseResult(T res) {

        if (isSearch) {
            parseSearch(res);
        } else {
            parseAuthorizedResponse(res);
        }
        parseMeResponse(res);
        parseServerTime(res);
        parseEvents(res);
    }

    public void parseAuthorizedResponse(T res) {
        if (res instanceof AuthorizationResponse) {
            try {
                credentials.saveSessionToken(
                        ((AuthorizationResponse) res).getAuthorization().getSessionToken());
            } catch (Exception e) {
                Log.e(TAG, "AuthorizationResponse is empty", e);
            }
            try {
                credentials.saveUserId(
                        ((AuthorizationResponse) res).getMe().getId());
            } catch (Exception e) {
                Log.e(TAG, "User ID is empty", e);
            }
            try {
                /*
                User me = ((AuthorizationResponse) res).getMe();
                UsersLocation usersLocation = null;
                try {
                    usersLocation = SoulVariableUtil.getUsersLocation(me);
                    LogUtils.updateLocation(UsersLocation.convertToAndroidLocation(usersLocation, ""));
                } catch (Exception e) {
                }
                try {
                    // TODO:  FIXME: 22.4.16 - If it is the first time, we don't have a location for user - check for null?
                    usersLocation.setUpdatedTime(
                            SoulStorage.getLong(SoulStorage.LAST_TIME_LOCATION_UPDATED));
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                TmpStorage.setCurrentUser(((AuthorizationResponse) res).getMe());
                // LogUtils.setUserId(((AuthorizationResponse) res).getMe().getId());
            } catch (Exception e) {
                Log.e(TAG, "CurrentUser saving error", e);
            }
            if (authSource != null) credentials.saveLastSuccessfulLoginSource(authSource);
            //TODO TEMPORAL
            if (authSource == Constants.PASS_LOGIN_SOURCE) {
                TmpStorage.save(TEMP_LOGIN, tempLogin);
                TmpStorage.save(TEMP_PASS, tempPass);
            }
        }
    }

    public void parseSearch(T res) {
        if ((res instanceof UsersSearchRESP) && (((UsersSearchRESP) res).getUsers()) != null) {
            // SearchResultManager.generateAndSavePageToken();
        }
    }

    public void parseMeResponse(T obj) {
        Observable.just(obj)
                .map(ResponseParser.this::parseMeResponse_)
                .subscribe(res -> {}, ResponseParser.this::onError);
    }

    public Void onError(Throwable e) {
        Log.e(TAG, "CurrentUser saving error", e);
        return null;
    }

    public Void parseMeResponse_(T res) {
        try {
            TmpStorage.setCurrentUser(((CurrentUserRESP) res).getCurrentUser());
        } catch (Exception e) {
        }
        return null;
    }

    public void parseServerTime(T res) {
        if (res instanceof GeneralResponse) {
            try {
                TimeUtils.saveServerTime(
                        TimeUtils.formatTimeFromAPI(
                                ((GeneralResponse) res).getAdditionalInfo().getServerTime()));
            } catch (Exception e) {
                Log.e(TAG, "Server time parsing error", e);
            }
        }
    }

    public void parseEvents(T res) {
        if (res instanceof EventsRESP) {
            try {
                EventManager.saveLastEvent((EventsRESP) res);
            } catch (Exception e) {
                Log.e(TAG, "Events parsing error", e);
            }
        }
    }

    private void parseLogout() {
        if (isLogout) {
            credentials.clearCredentials();
        }
    }
}

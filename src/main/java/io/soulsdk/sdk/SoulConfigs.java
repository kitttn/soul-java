package io.soulsdk.sdk;

import log.Log;

import io.soulsdk.configs.Config;
import io.soulsdk.network.adapter.NetworkAdapter;
import io.soulsdk.network.adapter.RetrofitAdapter;

/**
 * Provides a list of methods for initial settings and configuration of SDK. All settings are
 * not required. There are default values for all of these settings.
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class SoulConfigs {

    public static final long HUNDRED_YEARS = 100 * 365 * 24 * 60 * 60;
    public static final long ONE_YEAR = 365 * 24 * 60 * 60;
    public static final long HALF_YEAR = 183 * 24 * 60 * 60;
    public static final long ONE_MONTH = 30 * 24 * 60 * 60;
    public static final long ONE_WEEK = 7 * 24 * 60 * 60;
    public static final long ONE_DAY = 24 * 60 * 60;
    public static final long ONE_HOUR = 60 * 60;
    public static final long _7_MINS = 7 * 60;
    public static final long _20_MINS = 20 * 60;
    public static final long _5_MINS = 5 * 60;
    public static final long _5_MINS_IN_MILS = 5 * 60 * 1000;
    public static final long _2_MINS = 2 * 60;
    public static final long _1_MINS = 1 * 60;
    public static final long _1_MINS_IN_MILS = 1 * 60 * 1000;
    public static final long _30_SECS = 1 * 30;
    public static final long _1_SECS_IN_MILS = 1 * 1000;
    public static final long _5_SECS_IN_MILS = 5 * 1000;
    public static final long _30_SECS_IN_MILS = 30 * 1000;
    public static final long _45_SECS_IN_MILS = 45 * 1000;
    public static final long _60_SECS_IN_MILS = 60 * 1000;
    private static final String TAG = "SoulConfigs";
    private static String pubKey = "";
    private static String subKey = "";
    private NetworkAdapter adapter;
    private long searchLifeTimeValue = Config.DEFAULT_USERS_LIFETIME;
    private long likeTTL = Config.DEFAULT_LIKE_REACTION_LIFE_TIME;
    private long dislikeTTL = Config.DEFAULT_DISLIKE_REACTION_LIFE_TIME;
    private long blockTTL = Config.DEFAULT_BLOCK_REACTION_LIFE_TIME;

    private static String appName = Config.DEFAULT_USER_AGENT_APP_NAME;
    private static String appVersion = Config.DEFAULT_USER_AGENT_APP_VERSION;

    public SoulConfigs(String serverHost) {
        adapter = new RetrofitAdapter(serverHost);
    }

    //static void initialize(NetworkAdapter ad) {
  /*  static void initialize(String serverHost) {
        adapter = new RetrofitAdapter(serverHost);
    }*/

    public static void setChatsCredentials(String publishKey, String subscribeKey) {
        pubKey = publishKey;
        subKey = subscribeKey;
    }

   /* public static void setGCMSenderId(String id) {
        SoulStorage.save(FCM_SENDER_ID, id);
    }

    public static String getGCMSenderId() {
        return SoulStorage.getString(FCM_SENDER_ID);
    }*/

    /**
     * Retrieve search lifetime value.
     *
     * @return the search life time value. If it was not being set before USERS_LIFETIME_VALUE will be
     * returned.
     */
    public long getSearchLifeTimeValue() {
        return searchLifeTimeValue;
    }

    /**
     * Sets search lifetime value. It means the value availableTill of
     * {@link io.soulsdk.model.dto.Filterable} of {@link SoulCurrentUser} will be set as
     * [ServerTime + this value] each time after {@link SoulCurrentUser#turnSearchOn()}
     * will be called.
     * The value [availableTill] is time until which User will have possibility to search other
     * users, and other users will be able to find current user. This setting is not required.
     * There is default value USERS_LIFETIME_VALUE.
     *
     * @param val value in milliseconds
     */
    public void setSearchLifeTimeValue(long val) {
        searchLifeTimeValue = val;
    }

    /**
     * Sets lifetime for all existing reactions (like, dislike, block etc.)
     *
     * @param val value in milliseconds. This setting is not required.
     *            There is default value DEFAULT_REACTION_LIFE_TIME.
     */
    public void setAllReactionsLifeTime(long val) {
        likeTTL = dislikeTTL = blockTTL = val;
    }

    /**
     * Retrieve Like-reaction lifetime value.
     *
     * @return the Like-reaction lifetime value. If it was not being set before
     * DEFAULT_LIKE_REACTION_LIFE_TIME will be returned.
     */
    public long getLikeReactionLifeTime() {
        return likeTTL;
    }

    /**
     * Sets lifetime for "like" reaction.
     *
     * @param val value in milliseconds. This setting is not required.
     *            There is default value DEFAULT_LIKE_REACTION_LIFE_TIME.
     */
    public void setLikeReactionLifeTime(long val) {
        likeTTL = val;
    }

    /**
     * Retrieve Dislike-reaction lifetime value.
     *
     * @return the Dislike-reaction lifetime value. If it was not being set before
     * DEFAULT_DISLIKE_REACTION_LIFE_TIME will be returned.
     */
    public long getDislikeReactionLifeTime() {
        return dislikeTTL;
    }

    /**
     * Sets lifetime for "dislike" reaction.
     *
     * @param val value in milliseconds. This setting is not required.
     *            There is default value DEFAULT_DISLIKE_REACTION_LIFE_TIME.
     */
    public void setDislikeReactionLifeTime(long val) {
        dislikeTTL = val;
    }

    /**
     * Retrieve Block-reaction lifetime value.
     *
     * @return the Block-reaction lifetime value. If it was not being set before
     * DEFAULT_BLOCK_REACTION_LIFE_TIME will be returned.
     */
    public long getBlockReactionLifeTime() {
        return blockTTL;
    }

    /**
     * Sets lifetime for "block" reaction.
     *
     * @param val value in milliseconds. This setting is not required.
     *            There is default value DEFAULT_BLOCK_REACTION_LIFE_TIME.
     */
    public void setBlockReactionLifeTime(long val) {
        blockTTL = val;
    }

    /**
     * Sets app name for User-Agent of HTTP-requests
     *
     * @param val short name of application for sending as a parameter in User-Agent of
     *            HTTP-requests. This setting is not required. There is default value
     *            DEFAULT_USER_AGENT_APP_NAME.
     */
    public void setUserAgentAppName(String val) {
        appName = val;
        if (checkInit()) adapter.refreshUserAgent();
    }

    /**
     * Sets app version for User-Agent of HTTP-requests
     *
     * @param val version of application for sending as a parameter in User-Agent of
     *            HTTP-requests. This setting is not required. There is default value
     *            DEFAULT_USER_AGENT_APP_VERSION.
     */
    public void setUserAgentAppVersion(String val) {
        appVersion = val;
        if (checkInit()) adapter.refreshUserAgent();
    }

    public static String getUserAgentAppName() {
        return appName;
    }

    public static String getUserAgentAppVersion() {
        return appVersion;
    }

    public String getPublishKey() {
        return pubKey;
    }

    public String getSubscribeKey() {
        return subKey;
    }

    private boolean checkInit() {
        if (adapter == null) {
            Log.e(TAG, "checkApiKeyExists: " + " SDK mast be initialized");
            return false;
        }
        return true;
    }
}

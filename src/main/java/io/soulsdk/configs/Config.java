package io.soulsdk.configs;

/**
 * SDK configuration class
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class Config {

    public static final String PLATFORM = "SoulSDK";

    public static final long DEFAULT_REACTION_LIFE_TIME = 3 * 365 * 24 * 60 * 60; //3 years in unixtime format
    public static final int DEFAULT_EVENTS_LIMIT = 100;

    public static final long DEFAULT_USERS_LIFETIME = DEFAULT_REACTION_LIFE_TIME;
    public static final long DEFAULT_LIKE_REACTION_LIFE_TIME = DEFAULT_REACTION_LIFE_TIME;
    public static final long DEFAULT_DISLIKE_REACTION_LIFE_TIME = DEFAULT_REACTION_LIFE_TIME;
    public static final long DEFAULT_BLOCK_REACTION_LIFE_TIME = DEFAULT_REACTION_LIFE_TIME;
    public static final String DEFAULT_USER_AGENT_APP_NAME = "SoulClient";
    public static final String DEFAULT_USER_AGENT_APP_VERSION = "0.0.0";

}

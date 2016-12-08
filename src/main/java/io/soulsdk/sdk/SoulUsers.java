package io.soulsdk.sdk;

import com.google.gson.JsonObject;

import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.responses.RecommendFiltersRESP;
import io.soulsdk.model.responses.UserCustomSearchRESP;
import io.soulsdk.model.responses.UserRESP;
import io.soulsdk.model.responses.UsersSearchRESP;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import rx.Observable;

/**
 * Provides a collection of methods for retrieving users using different filters.
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class SoulUsers {

    private ServerAPIHelper helper;

    public SoulUsers(ServerAPIHelper hp) {
        helper = hp;
    }

    /**
     * <p>
     * Returns users sorted by rules defined in your Application Configuration Page in back-end
     * administration console. By default method always returns users that was not seen with current
     * viewingSession or was not ever reacted, or its reactions are expired.
     * By default only users that has availableTill parameter set to unixtime endpoint somewhere in
     * the future will be returned by this method.
     * </p>
     * (but this behaviour can be changed in the Application Configuration Page in back-end
     * administration console)
     *
     * @param soulCallback general {@link SoulCallback} of {@link UsersSearchRESP}
     */
    public void getNextSearchResult(String pageToken, String viewingSession, SoulCallback<UsersSearchRESP> soulCallback) {
        helper.getRecommendations(pageToken, viewingSession, soulCallback);
    }

    /**
     * <p>
     * Returns users sorted by rules defined in your Application Configuration Page in back-end
     * administration console. By default method always returns users that was not seen with current
     * viewingSession or was not ever reacted, or its reactions are expired.
     * By default only users that has availableTill parameter set to unixtime endpoint somewhere in
     * the future will be returned by this method.
     * </p>
     * (but this behaviour can be changed in the Application Configuration Page in back-end
     * administration console)
     *
     * @return observable of general {@link SoulResponse} of {@link UsersSearchRESP}
     */
    public Observable<SoulResponse<UsersSearchRESP>> getNextSearchResult(String pageToken, String viewingSession) {
        return helper.getRecommendations(pageToken, viewingSession, null);
    }

    /**
     * Returns a pageable list of users filtered by a predefined filter {filterName}
     *
     * @param filterName   string value as defined in your Application Configuration Page in
     *                     back-end administration console. Note that you cannot user
     *                     "recommendations" as a filter name, because it's reserved.
     * @param after        if set, returns only items with recordId larger than after
     * @param limit        specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @param soulCallback general {@link SoulCallback} of {@link UserCustomSearchRESP}
     */
    public void getNextCustomSearchResult(String filterName, Integer after, Integer limit, Long since, SoulCallback<UserCustomSearchRESP> soulCallback) {
        helper.getNextUsersCustomSearchResult(filterName, after, limit, since, soulCallback);
    }

    /**
     * Returns a pageable list of users filtered by a predefined filter {filterName}
     *
     * @param filterName string value as defined in your Application Configuration Page in
     *                   back-end administration console. Note that you cannot user
     *                   "recommendations" as a filter name, because it's reserved.
     * @param after      if set, returns only items with recordId larger than after
     * @param limit      specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @return observable of general {@link SoulResponse} of {@link UserCustomSearchRESP}
     */
    public Observable<SoulResponse<UserCustomSearchRESP>>
    getNextCustomSearchResult(String filterName, Integer after, Integer limit, Long since) {
        return helper.getNextUsersCustomSearchResult(filterName, after, limit, since, null);
    }

    public void getRecommendationsFilter(SoulCallback<RecommendFiltersRESP> soulCallback) {
        helper.getRecommendationsFilter(soulCallback);
    }

    public Observable<SoulResponse<RecommendFiltersRESP>>
    getRecommendationsFilter() {
        return helper.getRecommendationsFilter(null);
    }

    public Observable<SoulResponse<RecommendFiltersRESP>>
    patchRecommendationsFilter(JsonObject filters) {
        return helper.patchRecommendationsFilter(filters, null);
    }

    /**
     * Returns single user by specified userId. <b>Note:</b> it is possible to access only the user
     * you've already loaded using {@link #getNextSearchResult(String, String, SoulCallback)} or
     * {@link #getNextCustomSearchResult(String, Integer, Integer, Long, SoulCallback)}
     *
     * @param userId       id of specified user
     * @param soulCallback general {@link SoulCallback} of {@link UserRESP}
     */
    public void getUser(String userId, SoulCallback<UserRESP> soulCallback) {
        helper.getUser(userId, soulCallback);
    }

    /**
     * Returns single user by specified userId. <b>Note:</b> it is possible to access only the user
     * you've already loaded using {@link #getNextSearchResult(String, String)} or
     * {@link #getNextCustomSearchResult(String, Integer, Integer, Long)}.
     *
     * @param userId id of specified user
     * @return observable of general {@link SoulResponse} of {@link UserRESP}
     */
    public Observable<SoulResponse<UserRESP>> getUser(String userId) {
        return helper.getUser(userId, null);
    }

}

package io.soulsdk.sdk;

import log.Log;

import io.soulsdk.model.general.GeneralResponse;
import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.requests.ReactionREQ;
import io.soulsdk.model.requests.ReportUserREQ;
import io.soulsdk.model.responses.UserRESP;
import io.soulsdk.model.tasks.GeneralTask;
import io.soulsdk.model.tasks.ReactionTask;
import io.soulsdk.model.tasks.ReportTask;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import rx.Observable;
import rx.functions.Action1;

/**
 * <p>
 * Provides a list of methods for reacting to User or patch reaction was made before.
 * Reactions are typed, for example the type "liking" can be a enum of two different reaction types
 * - "liked" and "disliked".<br/>
 * The type "blocking" can contain one reaction type named "blocked".
 * Only one reaction of a type can be set at a time, a user cannot be "liked" and "disliked" at the
 * same time. Though any type can contain no reactions at all, thus "liking" type can be null
 * (that means that the user has not reacted yet, or deleted his/her reaction).<br/>
 * Different reactions can trigger different events. For example, if you and your partner added
 * reactions of type "liked" in reaction type "liking", a chat can be triggered; or if one of the
 * users reacted with "block" within reaction type "blocking", the users will not be able to see
 * each other any more.
 * </p>
 * <p>
 * <b></>Note:</b></> There are some simple methods to use most popular types of reactions.
 * </p>
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class SoulReactions {

    private final String TAG = "SoulReactions";

    public static final String REACTION_TYPE_LIKING = "likes";
    public static final String REACTION_TYPE_BLOCKING = "blocking";
    public static final String REACTION_LIKE = "liked";
    public static final String REACTION_DISLIKE = "dislike";
    public static final String REACTION_BLOCK = "blocked";

    private ServerAPIHelper helper;
    private SoulConfigs configs;

    public SoulReactions(SoulConfigs configs, ServerAPIHelper hp) {
        this.configs = configs;
        helper = hp;
    }


    //=================         Popular Easy Reactions        ======================================


    /**
     * Sends "like" reaction to specified user.
     *
     * @param userId       id of specified user
     * @param soulCallback general {@link SoulCallback} of {@link UserRESP}
     */
    public void likeUser(String userId, SoulCallback<UserRESP> soulCallback) {
        sendReaction(createLikeTask(userId), soulCallback);
    }

    /**
     * Sends "dislike" reaction to specified user.
     *
     * @param userId       id of specified user
     * @param soulCallback general {@link SoulCallback} of {@link UserRESP}
     */
    public void dislikeUser(String userId, SoulCallback<UserRESP> soulCallback) {
        sendReaction(createDislikeTask(userId), soulCallback);
    }

    /**
     * Sends "block" reaction to specified user.
     *
     * @param userId       id of specified user
     * @param soulCallback general {@link SoulCallback} of {@link UserRESP}
     */
    public void blockUser(String userId, SoulCallback<UserRESP> soulCallback) {
        sendReaction(createBlockTask(userId), soulCallback);
    }

    /**
     * Sends "like" reaction to specified user.
     *
     * @param userId id of specified user
     * @return observable of general {@link SoulResponse} of {@link UserRESP}
     */
    public Observable<SoulResponse<UserRESP>> likeUser(String userId) {
        return sendReaction(createLikeTask(userId));
    }

    /**
     * Sends "dislike" reaction to specified user.
     *
     * @param userId id of specified user
     * @return observable of general {@link SoulResponse} of {@link UserRESP}
     */
    public Observable<SoulResponse<UserRESP>> dislikeUser(String userId) {
        return sendReaction(createDislikeTask(userId));
    }

    /**
     * Sends "block" reaction to specified user.
     *
     * @param userId id of specified user
     * @return observable of general {@link SoulResponse} of {@link UserRESP}
     */
    public Observable<SoulResponse<UserRESP>> blockUser(String userId) {
        return sendReaction(createBlockTask(userId));
    }

    /**
     * Sends "like" reaction to specified user.
     * <p>
     * This method will try to patch User object. Data should be uploaded automatically but there is
     * no specific time when this will happen. If HTTP-request will failed or the internet
     * connection is not available this method will schedule background task to send it later when
     * connection will be restored and will try to send data to server until success even if user
     * stopped and relaunched the application.
     * <p>
     *
     * @param userId id of specified user
     */
    public void likeUserEventually(String userId) {
        sendReactionEventually(createLikeTask(userId));
    }

    /**
     * Sends "dislike" reaction to specified user.
     * <p>
     * This method will try to patch User object. Data should be uploaded automatically but there is
     * no specific time when this will happen. If HTTP-request will failed or the internet
     * connection is not available this method will schedule background task to send it later when
     * connection will be restored and will try to send data to server until success even if user
     * stopped and relaunched the application.
     * <p>
     *
     * @param userId id of specified user
     */
    public void dislikeUserEventually(String userId) {
        sendReactionEventually(createDislikeTask(userId));
    }

    /**
     * Sends "block" reaction to specified user.
     * <p>
     * This method will try to patch User object. Data should be uploaded automatically but there is
     * no specific time when this will happen. If HTTP-request will failed or the internet
     * connection is not available this method will schedule background task to send it later when
     * connection will be restored and will try to send data to server until success even if user
     * stopped and relaunched the application.
     * <p>
     *
     * @param userId id of specified user
     */
    public void blockUserEventually(String userId) {
        sendReactionEventually(createBlockTask(userId));
    }

    /**
     * Sends report with specified reason and report text about specified user
     * <p>
     * This method will try to patch User object. Data should be uploaded automatically but there is
     * no specific time when this will happen. If HTTP-request will failed or the internet
     * connection is not available this method will schedule background task to send it later when
     * connection will be restored and will try to send data to server until success even if user
     * stopped and relaunched the application.
     * <p>
     *
     * @param userId        id of specified user
     * @param reportUserREQ the object with report details
     */
    public void flagUserEventually(String userId, ReportUserREQ reportUserREQ) {
        flagUserEventually(createFlagTask(userId, reportUserREQ));
    }

    //========================     Default API Requests      =======================================


    /**
     * Sets reaction of specified reaction type for specified user
     *
     * @param userId       id of specified user
     * @param reactingType the name of reaction type (for example: "liking", "blocking")
     * @param reactionREQ  the object with reaction details ((for example: "like" and time of
     *                     reaction expiration)
     * @param soulCallback general {@link SoulCallback} of {@link UserRESP}
     */
    public void sendReactionToUser(String userId, String reactingType, ReactionREQ reactionREQ, SoulCallback<UserRESP> soulCallback) {
        helper.sendReactionToUser(userId, reactingType, reactionREQ, soulCallback);
    }

    /**
     * Sets reaction of specified reaction type for specified user
     *
     * @param userId       id of specified user
     * @param reactingType the name of reaction type (for example: "liking", "blocking")
     * @param reactionREQ  the object with reaction details ((for example: "like" and time of
     *                     reaction expiration)
     * @return observable of general {@link SoulResponse} of {@link UserRESP}
     */
    public Observable<SoulResponse<UserRESP>> sendReactionToUser(String userId, String reactingType, ReactionREQ reactionREQ) {
        return helper.sendReactionToUser(userId, reactingType, reactionREQ, null);
    }

    /**
     * Deletes reaction of specified reaction type for specified user.
     *
     * @param userId       id of specified user
     * @param reactingType the name of reaction type (for example: "liking", "blocking")
     * @param soulCallback general {@link SoulCallback} of {@link Boolean}
     */
    public void deleteReactionFromUser(String userId, String reactingType, SoulCallback<Boolean> soulCallback) {
        helper.deleteReactionToUser(userId, reactingType, soulCallback);
    }

    /**
     * Deletes reaction of specified reaction type for specified user.
     *
     * @param userId       id of specified user
     * @param reactingType the name of reaction type (for example: "liking", "blocking")
     * @return observable of general {@link SoulResponse} of {@link Boolean}
     */
    public Observable<SoulResponse<GeneralResponse>> deleteReactionFromUser(String userId, String reactingType) {
        return helper.deleteReactionToUser(userId, reactingType, null);
    }

    /**
     * Sends report with specified reason and report text about specified user
     *
     * @param userId        id of specified user
     * @param reportUserREQ the object with report details
     * @param soulCallback  general {@link SoulCallback} of {@link Boolean}
     */
    public void reportUser(String userId, ReportUserREQ reportUserREQ, SoulCallback<GeneralResponse> soulCallback) {
        helper.flagUser(userId, reportUserREQ, soulCallback);
    }

    /**
     * Sends report with specified reason and report text about specified user
     *
     * @param userId        id of specified user
     * @param reportUserREQ the object with report details
     * @return observable of general {@link SoulResponse} of {@link Boolean}
     */
    public Observable<SoulResponse<GeneralResponse>> reportUser(String userId, ReportUserREQ reportUserREQ) {
        return helper.flagUser(userId, reportUserREQ, null);
    }

    /**
     * Deletes report was sent about specified user before.
     *
     * @param userId       id of specified user
     * @param soulCallback general {@link SoulCallback} of {@link Boolean}
     */
    public void deleteFlagUser(String userId, SoulCallback<GeneralResponse> soulCallback) {
        helper.deleteFlagUser(userId, soulCallback);
    }

    /**
     * Deletes report was sent about specified user before.
     *
     * @param userId id of specified user
     * @return observable of general {@link SoulResponse} of {@link Boolean}
     */
    public Observable<SoulResponse<GeneralResponse>> deleteFlagUser(String userId) {
        return helper.deleteFlagUser(userId, null);
    }


    //============================     Private Utils Methods      ===========================================


    private void sendReaction(ReactionTask task, SoulCallback<UserRESP> soulCallback) {
        helper.sendReactionToUser(
                task.getUserId(), task.getReactingType(), task.getReactionREQ(), soulCallback);
    }

    private Observable<SoulResponse<UserRESP>> sendReaction(ReactionTask task) {
        return helper.sendReactionToUser(
                task.getUserId(), task.getReactingType(), task.getReactionREQ(), null);
    }

    private void sendReactionEventually(final ReactionTask task) {
        helper.sendReactionToUser(
                task.getUserId(), task.getReactingType(), task.getReactionREQ(), null)
                .subscribe(
                        new Action1<SoulResponse<UserRESP>>() {
                            @Override
                            public void call(SoulResponse<UserRESP> res) {
                                parseEventualResponse(res, task);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable e) {
                                handleErrorTask(e, task);
                            }
                        });
    }

    private void flagUserEventually(final ReportTask task) {
        helper.flagUser(task.getUserId(), task.getReportUserREQ(), null)
                .subscribe(
                        res -> {
                            parseEventualResponse(res, task);
                        },
                        e -> {
                            handleErrorTask(e, task);
                        });
    }

    private ReactionTask createLikeTask(String userId) {
        return new ReactionTask(
                userId,
                REACTION_TYPE_LIKING,
                new ReactionREQ(REACTION_LIKE, SoulSystem.getServerTime() / 1000 + configs.getLikeReactionLifeTime()));
    }

    private ReactionTask createDislikeTask(String userId) {
        return new ReactionTask(
                userId,
                REACTION_TYPE_LIKING,
                new ReactionREQ(REACTION_DISLIKE, SoulSystem.getServerTime() / 1000 + configs.getDislikeReactionLifeTime()));
    }

    private ReactionTask createBlockTask(String userId) {
        return new ReactionTask(
                userId,
                REACTION_TYPE_BLOCKING,
                new ReactionREQ(REACTION_BLOCK, SoulSystem.getServerTime() / 1000 + configs.getBlockReactionLifeTime()));
    }

    private ReportTask createFlagTask(String userId, ReportUserREQ reportUserREQ) {
        return new ReportTask(userId, reportUserREQ);
    }

    private void parseEventualResponse(SoulResponse response, GeneralTask task) {
        if (response != null && response.getError() != null)
            Log.e(TAG, "parseEventualResponse: " + response.getError().getCode() + " : "
                    + response.getError().getDescription());
        //TODO make future task to save eventually
    }

    private void handleErrorTask(Throwable e, GeneralTask task) {
        //TODO make future task to save eventually
        Log.e(TAG, "handleErrorTask: " + e.getMessage());
    }

}
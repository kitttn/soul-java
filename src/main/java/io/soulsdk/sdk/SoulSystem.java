package io.soulsdk.sdk;

import io.soulsdk.util.storage.TmpStorage;

/**
 * Provides system information from server-side and SDK.
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class SoulSystem {

    private SoulSystem() {
    }

    /**
     * Provides time on back-end Server. This time value is always is up-to-dated. The value calculated
     * as current time of a device plus difference between device's time and server's time.
     * Correction happens every time the SDK receives successful HTTP-response form back-end for
     * each one HTTP-request.
     *
     * @return the server time in milliseconds
     */
    public static long getServerTime() {
        return TmpStorage.getServerTimeDiff() + System.currentTimeMillis();
    }


}
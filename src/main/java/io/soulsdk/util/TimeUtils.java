package io.soulsdk.util;

import io.soulsdk.util.storage.TmpStorage;

/**
 * Created by buyaroff1 on 22/01/16.
 */
public class TimeUtils {

    public static Double formatTimeToAPI(long time) {
        return Double.valueOf(time / 1000);
    }

    public static long formatTimeFromAPI(double time) {
        return (long) (time * 1000);
    }

    public static void saveServerTime(long serverTime) {
        long diff = serverTime - System.currentTimeMillis();
        //  Log.d("TIME DIFF: ", "" + diff + " = " + (int) diff / 1000);
        TmpStorage.setServerTimeDiff(diff);
    }
}

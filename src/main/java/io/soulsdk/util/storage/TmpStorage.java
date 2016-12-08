package io.soulsdk.util.storage;
/*

import android.location.Location;
import android.support.annotation.Nullable;
*/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import io.soulsdk.model.dto.User;
import log.Log;

/**
 * @author kitttn
 */

public class TmpStorage {
    private static final String TAG = "TmpStorage";
    private static User currentUser;
    // private static Location location;
    private static long serverTimeDiff;
    private static HashMap<String, Serializable> map = new HashMap<>();

    public static void init() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(".storage"));
            map = (HashMap<String, Serializable>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "init: Couldn't restore storage!");
            map = new HashMap<>();
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        TmpStorage.currentUser = currentUser;
    }
/*

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        TmpStorage.location = location;
    }
*/

    public static long getServerTimeDiff() {
        return serverTimeDiff;
    }

    public static void setServerTimeDiff(long serverTimeDiff) {
        TmpStorage.serverTimeDiff = serverTimeDiff;
    }

    public static void save(String key, Serializable value) {
        map.put(key, value);
        serialize();
    }

    private static void serialize() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(".storage"));
            oos.writeObject(map);
            oos.close();
            // Log.w(TAG, "serialize: >> Storage written!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key) {
        return map.get(key);
    }
}

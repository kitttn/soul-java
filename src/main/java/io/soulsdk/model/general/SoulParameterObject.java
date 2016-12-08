package io.soulsdk.model.general;

import com.google.gson.JsonObject;

/**
 * //
 *
 * @author Buiarov Iurii
 * @version 0.15
 * @since 04/04/16
 */
public class SoulParameterObject {

    private static final String TAG = "SoulParameterObject";

    private final transient JsonObject estimatedData;

    public SoulParameterObject() {
        estimatedData = new JsonObject();
    }

    public SoulParameterObject(JsonObject estimatedData) {
        if (estimatedData != null)
            this.estimatedData = estimatedData;
        else this.estimatedData = new JsonObject();
    }

    public JsonObject asJson() {
        return estimatedData;
    }

    private void put(String key, Object val) {
        if (!isKeyValid(key)) {
            throw new IllegalArgumentException("Invalid key");
        }
        //  estimatedData.addProperty(key, val);
    }

    private void assertKey(String key) {
        if (!isKeyValid(key)) {
            throw new IllegalArgumentException("Invalid key");
        }
    }

    private void throwCast(String key) {
        //  throw new IllegalArgumentException("Invalid type of property: " + key);
        //Log.e(TAG, "Invalid type of property: " + key);
    }

    private boolean isKeyValid(String key) {
        return !(key == null || key.equals(""));
    }

    public void setProperty(String key, String val) {
        // put(key, val);
        //val = val.replace("\\\\", "");
        assertKey(key);
        estimatedData.addProperty(key, val);
    }

    public String getString(String property) {
        try {
            return estimatedData.get(property).getAsString();
        } catch (Exception e) {
            throwCast(property);
            return null;
        }
    }

    public void setProperty(String key, Number val) {
        //put(key, val);
        assertKey(key);
        estimatedData.addProperty(key, val);
    }

    public Double getDouble(String property) {
        try {
            return estimatedData.get(property).getAsDouble();
        } catch (Exception e) {
            throwCast(property);
            return null;
        }
    }

    public Float getFloat(String property) {
        try {
            return estimatedData.get(property).getAsFloat();
        } catch (Exception e) {
            throwCast(property);
            return null;
        }
    }

    public Long getLong(String property) {
        try {
            return estimatedData.get(property).getAsLong();
        } catch (Exception e) {
            throwCast(property);
            return null;
        }
    }

    public Integer getInt(String property) {
        try {
            return estimatedData.get(property).getAsInt();
        } catch (Exception e) {
            throwCast(property);
            return null;
        }
    }

    public void setProperty(String key, Boolean val) {
        // put(key, val);
        assertKey(key);
        estimatedData.addProperty(key, val);
    }

    public Boolean getBoolean(String property) {
        try {
            return estimatedData.get(property).getAsBoolean();
        } catch (Exception e) {
            throwCast(property);
            return null;
        }
    }
 /*   public void setProperty(String key, byte[] val) {
        put(key, val);
    }*/

/*    public void setProperty(String key, List val) {
        put(key, val);
    }*/

 /*   public void setProperty(String key, Map val) {
        put(key, val);
    }*/

    public void setProperty(String key, SoulParameterObject val) {
        //put(key, val);
        assertKey(key);
        estimatedData.add(key, val.asJson());
    }

    public SoulParameterObject getSoulParameterObject(String property) {
        try {
            return new SoulParameterObject(estimatedData.get(property).getAsJsonObject());
        } catch (Exception e) {
            throwCast(property);
            return null;
        }
    }

    public String toString() {
        return estimatedData.toString();
    }
}

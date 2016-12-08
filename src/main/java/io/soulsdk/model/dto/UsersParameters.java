package io.soulsdk.model.dto;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import io.soulsdk.model.general.SoulParameterObject;

/**
 * Different Parameters of User
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class UsersParameters {

    private JsonObject filterable;
    private JsonObject publicVisible;
    private JsonObject publicWritable;
    @SerializedName("private")
    private JsonObject privateVisible;


    public String getFilterableAsString() {
        return filterable.toString();
    }

    public SoulParameterObject getFilterable() {
        return new SoulParameterObject(filterable);
    }

    public JsonObject getFilterableAsJson() {
        return filterable;
    }

    public void setFilterable(SoulParameterObject filterable) {
        this.filterable = filterable.asJson();
    }

    public JsonObject getPublicVisibleAsJson() {
        return publicVisible;
    }

    public String getPublicVisibleAsString() {
        return publicVisible.toString();
    }

    public SoulParameterObject getPublicVisible() {
        return new SoulParameterObject(publicVisible);
    }

    public void setPublicVisible(SoulParameterObject publicVisible) {
        this.publicVisible = publicVisible.asJson();
    }

    public String getPublicWritableAsString() {
        return publicWritable.toString();
    }

    public SoulParameterObject getPublicWritable() {
        return new SoulParameterObject(publicWritable);
    }

    public JsonObject getPublicWritableAsJson() {
        return publicWritable;
    }

    public void setPublicWritable(SoulParameterObject publicWritable) {
        this.publicWritable = publicWritable.asJson();
    }

    public String getPrivateVisibleAsString() {
        return privateVisible.toString();
    }

    public SoulParameterObject getPrivateVisible() {
        return new SoulParameterObject(privateVisible);
    }

    public JsonObject getPrivateVisibleAsJson() {
        return privateVisible;
    }

    public void setPrivateVisible(SoulParameterObject privateVisible) {
        this.privateVisible = privateVisible.asJson();
    }
}

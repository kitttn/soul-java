package io.soulsdk.model.dto;

import io.soulsdk.model.general.SoulParameterObject;

/**
 * UsersLocation entity contain hidden property {updatedTime} using only locally on device
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class UsersLocation extends SoulParameterObject {

    private double lat;
    private double lng;
    private long updatedTime = -1;

    public UsersLocation() {
    }

    public UsersLocation(double lat, double lng) {
        setLat(lat);
        setLng(lng);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        setProperty("lat", lat);
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        setProperty("lng", lng);
        this.lng = lng;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "{Lat: " + lat + "; Long: " + lng + "}";
    }
}

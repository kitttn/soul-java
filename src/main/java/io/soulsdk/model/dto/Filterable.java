package io.soulsdk.model.dto;

import io.soulsdk.model.general.SoulParameterObject;

import static io.soulsdk.util.Constants.F_AVAILABLE_TILL;
import static io.soulsdk.util.Constants.F_BANNED;
import static io.soulsdk.util.Constants.F_GENDER;
import static io.soulsdk.util.Constants.F_LOCATION;
import static io.soulsdk.util.Constants.F_LOOKING_FOR;

/**
 * Filterable variables of User
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Filterable extends SoulParameterObject {

    private Boolean banned;
    private Long availableTill;
    private UsersLocation location;
    private String gender;
    private String lookingForGender;

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        setProperty(F_BANNED, banned);
        this.banned = banned;
    }

    public long getAvailableTill() {
        return availableTill;
    }

    public void setAvailableTill(long availableTill) {
        setProperty(F_AVAILABLE_TILL, availableTill);
        this.availableTill = availableTill;
    }

    public UsersLocation getLocation() {
        return location;
    }

    public void setLocation(UsersLocation location) {
        setProperty(F_LOCATION, location);
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        setProperty(F_GENDER, gender);
        this.gender = gender;
    }

    public String getLookingForGender() {
        return lookingForGender;
    }

    public void setLookingForGender(String lookingForGender) {
        setProperty(F_LOOKING_FOR, lookingForGender);
        this.lookingForGender = lookingForGender;
    }

}

package io.soulsdk.model.dto;

import io.soulsdk.model.general.SoulParameterObject;

/**
 * Variables of User that is visible for all other users
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class PublicVisible extends SoulParameterObject {

    private String headline;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        setProperty("headline", headline);
        this.headline = headline;
    }

}

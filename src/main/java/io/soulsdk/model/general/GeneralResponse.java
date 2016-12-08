package io.soulsdk.model.general;

import io.soulsdk.model.dto.AdditionalInfo;

/**
 * General Response
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class GeneralResponse {

    private AdditionalInfo additionalInfo;

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}

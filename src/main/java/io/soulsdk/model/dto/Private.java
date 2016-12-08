package io.soulsdk.model.dto;

/**
 * Private variables of User
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Private {

    private boolean selectedForTrial;
    private boolean disabledSound;
    private int UTC;

    public boolean isSelectedForTrial() {
        return selectedForTrial;
    }

    public void setSelectedForTrial(boolean selectedForTrial) {
        this.selectedForTrial = selectedForTrial;
    }

    public boolean isDisabledSound() {
        return disabledSound;
    }

    public void setDisabledSound(boolean disabledSound) {
        this.disabledSound = disabledSound;
    }

    public int getUTC() {
        return UTC;
    }

    public void setUTC(int UTC) {
        this.UTC = UTC;
    }
}

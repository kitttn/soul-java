package io.soulsdk.model.dto;

/**
 * Photo entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Photo {

    private String id;
    private float expiresTime;
    private ImageProperties original;
    private ImageProperties thumbnails;
    private boolean main;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(float expiresTime) {
        this.expiresTime = expiresTime;
    }

    public ImageProperties getOriginal() {
        return original;
    }

    public void setOriginal(ImageProperties original) {
        this.original = original;
    }

    public ImageProperties getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(ImageProperties thumbnails) {
        this.thumbnails = thumbnails;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }
}

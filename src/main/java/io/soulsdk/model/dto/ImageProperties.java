package io.soulsdk.model.dto;

/**
 * The Properties of image
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class ImageProperties {

    private String url;
    private int width;
    private int height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}

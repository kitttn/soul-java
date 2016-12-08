package io.soulsdk.model.dto;

import java.util.List;

/**
 * Album entity
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class Album {

    private String name;
    private String privacy;
    private int photoCount;
    private int order;
    private AlbumParameters parameters;
    private Photo mainPhoto;
    private List<Photo> photos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public AlbumParameters getParameters() {
        return parameters;
    }

    public void setParameters(AlbumParameters parameters) {
        this.parameters = parameters;
    }

    public Photo getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(Photo mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}

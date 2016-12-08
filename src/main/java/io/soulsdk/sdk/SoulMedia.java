package io.soulsdk.sdk;

import java.io.File;

import io.soulsdk.model.dto.Album;
import io.soulsdk.model.general.GeneralResponse;
import io.soulsdk.model.general.SoulCallback;
import io.soulsdk.model.general.SoulResponse;
import io.soulsdk.model.requests.CreateNewAlbumREQ;
import io.soulsdk.model.requests.PatchAlbumREQ;
import io.soulsdk.model.requests.PatchPhotoREQ;
import io.soulsdk.model.responses.AlbumRESP;
import io.soulsdk.model.responses.AlbumsRESP;
import io.soulsdk.model.responses.PhotoRESP;
import io.soulsdk.sdk.helpers.ServerAPIHelper;
import rx.Observable;

/**
 * <p>
 * Provides a list of methods for working with MediaFiles such ass Photo, Audio, Video
 * albums of Current User and other users.
 * </p>
 * Note that every user has an album named "default"
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */

public class SoulMedia {

    private ServerAPIHelper helper;

    public SoulMedia(ServerAPIHelper hp) {
        helper = hp;
    }

    /**
     * Returns a list of Current User's photo albums.
     *
     * @param offset       specifies how many results to skip for the first returned result
     * @param limit        specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @param soulCallback general {@link SoulCallback} of {@link AlbumsRESP}
     */
    public void getMyAlbums(Integer offset, Integer limit, SoulCallback<AlbumsRESP> soulCallback) {
        helper.getMyAlbums(offset, limit, soulCallback);
    }

    /**
     * Returns a list of Current User's photo albums.
     *
     * @param offset specifies how many results to skip for the first returned result
     * @param limit  specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @return observable of general {@link SoulResponse} of {@link AlbumsRESP}
     */
    public Observable<SoulResponse<AlbumsRESP>> getMyAlbums(Integer offset, Integer limit) {
        return helper.getMyAlbums(offset, limit, null);
    }

    /**
     * Creates new photo album for Current User
     *
     * @param createNewAlbumREQ object contains information required to create new album
     * @param soulCallback      general {@link SoulCallback} of {@link Boolean}
     */
    public void createNewAlbum(CreateNewAlbumREQ createNewAlbumREQ, SoulCallback<Boolean> soulCallback) {
        helper.createNewAlbum(createNewAlbumREQ, soulCallback);
    }

    /**
     * Creates new photo album for Current User
     *
     * @param createNewAlbumREQ object contain information required to create new album
     * @return observable of general {@link SoulResponse} of {@link Boolean}
     */
    public Observable<SoulResponse<Album>> createNewAlbum(CreateNewAlbumREQ createNewAlbumREQ) {
        return helper.createNewAlbum(createNewAlbumREQ, null);
    }

    /**
     * Returns photos, associated with Current User with paths to the originals and thumbnails.
     * Thumbnail sizes can be configured at your Application Configuration Page in back-end
     * administration console.
     *
     * @param albumName    the name of the album
     * @param offset       specifies how many results to skip for the first returned result
     * @param limit        specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @param soulCallback general {@link SoulCallback} of {@link AlbumRESP}
     */
    public void getPhotosFromMyAlbum(String albumName, Integer offset, Integer limit, SoulCallback<AlbumRESP> soulCallback) {
        helper.getPhotosFromMyAlbum(albumName, offset, limit, soulCallback);
    }

    /**
     * Returns photos, associated with Current User with paths to the originals and thumbnails.
     * Thumbnail sizes can be configured at your Application Configuration Page in back-end
     * administration console.
     *
     * @param albumName the name of the album
     * @param offset    specifies how many results to skip for the first returned result
     * @param limit     specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @return observable of general {@link SoulResponse} of {@link AlbumRESP}
     */
    public Observable<SoulResponse<AlbumRESP>> getPhotosFromMyAlbum(String albumName, Integer offset, Integer limit) {
        return helper.getPhotosFromMyAlbum(albumName, offset, limit, null);
    }

    /**
     * Attach a new photo to the Current User's album. Note that there is a limit to maximum number
     * of photos per user. The limit can be configured at your Application Configuration Page in
     * back-end administration console.
     *
     * @param albumName    the name of the album to add photo in
     * @param file         JPEG photo file, maximum size is limited to 5MB, note that 3G have high
     *                     latency, so it may be wise to compress the image on the client device
     *                     beforehand.
     * @param soulCallback general {@link SoulCallback} of {@link PhotoRESP}
     */
    public void addPhotoToMyAlbum(String albumName, File file, SoulCallback<PhotoRESP> soulCallback) {
        helper.addPhotoToMyAlbum(albumName, file, soulCallback);
    }

    /**
     * Attach a new photo to the Current User's album. Note that there is a limit to maximum number
     * of photos per user. The limit can be configured at your Application Configuration Page in
     * back-end administration console.
     *
     * @param albumName the name of the album to add photo in
     * @param file      JPEG photo file, maximum size is limited to 5MB, note that 3G have high
     *                  latency, so it may be wise to compress the image on the client device
     *                  beforehand.
     * @return observable of general {@link SoulResponse} of {@link PhotoRESP}
     */
    public Observable<SoulResponse<PhotoRESP>> addPhotoToMyAlbum(String albumName, File file) {
        return helper.addPhotoToMyAlbum(albumName, file, null);
    }

    /**
     * Edits specified Current User's album.
     *
     * @param albumName     the name of the album to patch
     * @param patchAlbumREQ object contains information available to update
     * @param soulCallback  general {@link SoulCallback} of {@link PatchAlbumREQ}
     */
    public void patchAlbum(String albumName, PatchAlbumREQ patchAlbumREQ, SoulCallback<PatchAlbumREQ> soulCallback) {
        helper.patchAlbum(albumName, patchAlbumREQ, soulCallback);
    }

    /**
     * Edits specified Current User's album.
     *
     * @param albumName     the name of the album to patch
     * @param patchAlbumREQ object contains information available to update
     * @return observable of general {@link SoulResponse} of {@link PhotoRESP}
     */
    public Observable<SoulResponse<PatchAlbumREQ>> patchAlbum(String albumName, PatchAlbumREQ patchAlbumREQ) {
        return helper.patchAlbum(albumName, patchAlbumREQ, null);
    }

    /**
     * Deletes specified Current User's album
     *
     * @param albumName    the name of the album photo patch from
     * @param soulCallback general {@link SoulCallback} of {@link Boolean}
     */
    public void deleteAlbum(String albumName, SoulCallback<GeneralResponse> soulCallback) {
        helper.deleteAlbum(albumName, soulCallback);
    }

    /**
     * Deletes specified Current User's album
     *
     * @param albumName the name of the album photo patch from
     * @return observable of general {@link SoulResponse} of {@link Boolean}
     */
    public Observable<SoulResponse<GeneralResponse>> deleteAlbum(String albumName) {
        return helper.deleteAlbum(albumName, null);
    }

    /**
     * Returns single Photo object from specified album of Current User
     *
     * @param albumName    the name of the album with specified photo
     * @param photoId      id of the photo
     * @param soulCallback general {@link SoulCallback} of {@link PhotoRESP}
     */
    public void getMyPhoto(String albumName, String photoId, SoulCallback<PhotoRESP> soulCallback) {
        helper.getMyPhoto(albumName, photoId, soulCallback);
    }

    /**
     * Returns single Photo object from specified album of Current User
     *
     * @param albumName the name of the album with specified photo
     * @param photoId   id of the photo
     * @return observable of general {@link SoulResponse} of {@link PhotoRESP}
     */
    public Observable<SoulResponse<PhotoRESP>> getMyPhoto(String albumName, String photoId) {
        return helper.getMyPhoto(albumName, photoId, null);
    }

    /**
     * Edits specified Photo object in specified album
     *
     * @param albumName     the name of the album with photo to edit
     * @param photoId       id of the photo
     * @param patchPhotoREQ object contains information available to update
     * @param soulCallback  general {@link SoulCallback} of {@link PhotoRESP}
     */
    public void patchMyPhoto(String albumName, String photoId, PatchPhotoREQ patchPhotoREQ, SoulCallback<PhotoRESP> soulCallback) {
        helper.patchMyPhoto(albumName, photoId, patchPhotoREQ, soulCallback);
    }

    /**
     * Edits specified Photo object in specified album
     *
     * @param albumName     the name of the album with photo to edit
     * @param photoId       id of the photo
     * @param patchPhotoREQ object contains information available to update
     * @return observable of general {@link SoulResponse} of {@link PhotoRESP}
     */
    public Observable<SoulResponse<PhotoRESP>> patchMyPhoto(String albumName, String photoId, PatchPhotoREQ patchPhotoREQ) {
        return helper.patchMyPhoto(albumName, photoId, patchPhotoREQ, null);
    }

    /**
     * Makes specified Photo as main in specified album
     *
     * @param albumName    the name of the album with photo to edit
     * @param photoId      id of the photo
     * @param soulCallback general {@link SoulCallback} of {@link PhotoRESP}
     */
    public void setPhotoAsMain(String albumName, String photoId, SoulCallback<PhotoRESP> soulCallback) {
        helper.setPhotoAsMain(albumName, photoId, soulCallback);
    }

    /**
     * Makes specified Photo as main in specified album
     *
     * @param albumName the name of the album with photo to edit
     * @param photoId   id of the photo
     * @return observable of general {@link SoulResponse} of {@link PhotoRESP}
     */
    public Observable<SoulResponse<PhotoRESP>> setPhotoAsMain(String albumName, String photoId) {
        return helper.setPhotoAsMain(albumName, photoId, null);
    }

    /**
     * Deletes specified photo from specified album
     *
     * @param albumName    the name of the album with photo to patch
     * @param photoId      id of the photo
     * @param soulCallback general {@link SoulCallback} of {@link Boolean}
     */
    public void deleteMyPhoto(String albumName, String photoId, SoulCallback<GeneralResponse> soulCallback) {
        helper.deleteMyPhoto(albumName, photoId, soulCallback);
    }

    /**
     * Deletes specified photo from specified album
     *
     * @param albumName the name of the album with photo to patch
     * @param photoId   id of the photo
     * @return observable of general {@link SoulResponse} of {@link Boolean}
     */
    public Observable<SoulResponse<GeneralResponse>> deleteMyPhoto(String albumName, String photoId) {
        return helper.deleteMyPhoto(albumName, photoId, null);
    }


    //========================       methods for other users        ================================


    /**
     * Returns all public albums of specified user. Note that every user has an album named
     * "default". Note, it is possible to access only the user you've already loaded using
     * {@link SoulUsers#getNextSearchResult()}
     *
     * @param userId       id of specified user
     * @param offset       specifies how many results to skip for the first returned result
     * @param limit        specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @param soulCallback general {@link SoulCallback} of {@link AlbumsRESP}
     */
    public void getUsersAlbums(String userId, Integer offset, Integer limit, SoulCallback<AlbumsRESP> soulCallback) {
        helper.getUsersAlbums(userId, offset, limit, soulCallback);
    }

    /**
     * Returns all public albums of specified user. Note that every user has an album named
     * "default". Note, it is possible to access only the user you've already loaded using
     * {@link SoulUsers#getNextSearchResult()}
     *
     * @param userId id of specified user
     * @param offset specifies how many results to skip for the first returned result
     * @param limit  specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @return observable of general {@link SoulResponse} of {@link AlbumsRESP}
     */
    public Observable<SoulResponse<AlbumsRESP>> getUsersAlbums(String userId, Integer offset, Integer limit) {
        return helper.getUsersAlbums(userId, offset, limit, null);
    }

    /**
     * Returns a list of photos from specified user's album with pagination by photos. Note, it is
     * possible to access only the user you've already loaded using {@link SoulUsers#getNextSearchResult()}
     *
     * @param userId       id of specified user
     * @param albumName    the name of the user's album
     * @param offset       specifies how many results to skip for the first returned result
     * @param limit        specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @param soulCallback general {@link SoulCallback} of {@link AlbumRESP}
     */
    public void getUsersPhotosFromAlbum(String userId, String albumName, Integer offset, Integer limit, SoulCallback<AlbumRESP> soulCallback) {
        helper.getUsersPhotosFromAlbum(userId, albumName, offset, limit, soulCallback);
    }

    /**
     * Returns a list of photos from specified user's album with pagination by photos. Note, it is
     * possible to access only the user you've already loaded using {@link SoulUsers#getNextSearchResult()}
     *
     * @param userId    id of specified user
     * @param albumName the name of the user's album
     * @param offset    specifies how many results to skip for the first returned result
     * @param limit     specifies how many results to return (default: 20; min: 1; maximum: 100))
     * @return observable of general {@link SoulResponse} of {@link AlbumsRESP}
     */
    public Observable<SoulResponse<AlbumRESP>> getUsersPhotosFromAlbum(
            String userId, String albumName, Integer offset, Integer limit) {
        return helper.getUsersPhotosFromAlbum(userId, albumName, offset, limit, null);
    }

    /**
     * Returns single Photo object from specified album of some another User
     *
     * @param userId       userId, which stores the album
     * @param albumName    the name of the album with specified photo
     * @param photoId      id of the photo
     * @param soulCallback general {@link SoulCallback} of {@link PhotoRESP}
     */
    public void getUsersPhoto(String userId, String albumName, String photoId, SoulCallback<PhotoRESP> soulCallback) {
        helper.getUsersPhoto(userId, albumName, photoId, soulCallback);
    }

    /**
     * Returns single Photo object from specified album of some another User
     *
     * @param userId    userId, which stores the album
     * @param albumName the name of the album with specified photo
     * @param photoId   id of the photo
     * @return observable of general {@link SoulResponse} of {@link PhotoRESP}
     */
    public Observable<SoulResponse<PhotoRESP>> getUsersPhoto(String userId, String albumName, String photoId) {
        return helper.getUsersPhoto(userId, albumName, photoId, null);
    }
}

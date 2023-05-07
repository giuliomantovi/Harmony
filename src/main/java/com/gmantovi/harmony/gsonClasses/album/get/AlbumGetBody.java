package com.gmantovi.harmony.gsonClasses.album.get;

import com.gmantovi.harmony.gsonClasses.album.AlbumData;
import com.google.gson.annotations.SerializedName;

/**
 * A class to denote the body part of the JSON.
 */
public class AlbumGetBody {
    @SerializedName("album")
    private AlbumData album;

    public void setAlbum(AlbumData album) {
        this.album = album;
    }

    public AlbumData getAlbum() {
        return album;
    }
}

package com.gmantovi.harmony.gsonClasses.album;

import com.google.gson.annotations.SerializedName;

/**
 * A class to denote the album entity.
 */
public class Album {
    @SerializedName("album") private AlbumData album;

    public void setAlbum(AlbumData track) {
        this.album = track;
    }

    public AlbumData getAlbum() {
        return album;
    }
}

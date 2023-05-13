package com.gmantovi.harmony.gsonClasses.artist.getAlbums;

import com.gmantovi.harmony.gsonClasses.album.Album;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A class to denote the body part of the JSON.
 *
 */
public class AlbumsGetBody {
    //@SerializedName("album_list")
    private List<Album> album_list;

    public void setAlbums(List<Album> album_list) {
        this.album_list = album_list;
    }

    public List<Album> getAlbums_list() {
        return album_list;
    }
}

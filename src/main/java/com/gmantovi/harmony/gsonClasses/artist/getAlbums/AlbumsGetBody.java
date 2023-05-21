/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.artist.getAlbums;

import com.gmantovi.harmony.gsonClasses.album.Album;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A class for json data retrieval.
 *
 * @author Giulio Mantovi
 * @version 2023.05.21
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

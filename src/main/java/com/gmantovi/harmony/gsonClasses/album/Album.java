/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.album;

import com.google.gson.annotations.SerializedName;

/**
 * A class to denote the album entity.
 * @author Giulio Mantovi
 * @version 2023.05.21
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

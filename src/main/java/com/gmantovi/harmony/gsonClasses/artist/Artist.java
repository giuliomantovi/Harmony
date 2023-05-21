/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.artist;

import com.google.gson.annotations.SerializedName;

/**
 * A class to denote the artist entity.
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public class Artist {
    @SerializedName("artist")
    private ArtistData artist;

    public void setArtist(ArtistData artist) {
        this.artist = artist;
    }

    public ArtistData getArtist() {
        return artist;
    }
}

/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.artist.get;

import com.gmantovi.harmony.gsonClasses.artist.ArtistData;
import com.google.gson.annotations.SerializedName;

/**
 * A class to denote the body part of the JSON.
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public class ArtistGetBody {
    @SerializedName("artist")
    private ArtistData artist;

    public void setArtist(ArtistData artist) {
        this.artist = artist;
    }

    public ArtistData getArtist() {
        return artist;
    }
}

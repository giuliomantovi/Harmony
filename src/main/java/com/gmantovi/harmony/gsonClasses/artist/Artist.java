package com.gmantovi.harmony.gsonClasses.artist;

import com.google.gson.annotations.SerializedName;

/**
 * A class to denote the track entity.
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

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

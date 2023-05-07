package com.gmantovi.harmony.gsonClasses.artist.search;

import com.google.gson.annotations.SerializedName;

public class ArtistSearchMessage {
	
    @SerializedName("message")
    private ArtistSearchContainer artistMessage;

    public void setArtistMessage(ArtistSearchContainer artistMessage) {
        this.artistMessage = artistMessage;
    }

    public ArtistSearchContainer getArtistMessage() {
        return artistMessage;
    }
}

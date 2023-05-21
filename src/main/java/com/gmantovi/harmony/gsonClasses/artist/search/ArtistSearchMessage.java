/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.artist.search;

import com.google.gson.annotations.SerializedName;
/**
 * A class for json data retrieval.
 *
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
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

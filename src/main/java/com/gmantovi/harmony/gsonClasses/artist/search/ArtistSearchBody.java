/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.artist.search;

import com.gmantovi.harmony.gsonClasses.artist.Artist;

import java.util.List;

/**
 * A class for json data retrieval.
 *
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class ArtistSearchBody {
    private List<Artist> artist_list;

    public void setArtist_list(List<Artist> artist_list) {
        this.artist_list = artist_list;
    }

    public List<Artist> getArtist_list() {
        return artist_list;
    }
}

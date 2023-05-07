package com.gmantovi.harmony.gsonClasses.artist.search;

import com.gmantovi.harmony.gsonClasses.artist.Artist;

import java.util.List;

/**
 * A class to denote the body part of the JSON.
 *
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

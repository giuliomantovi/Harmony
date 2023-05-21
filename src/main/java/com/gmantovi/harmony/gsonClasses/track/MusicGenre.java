/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in README
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.track;

import com.google.gson.annotations.SerializedName;
/**
 * A class to denote the musicGenre entity in the json response.
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public class MusicGenre {
    @SerializedName("music_genre_id")

    private Integer musicGenreId;
    @SerializedName("music_genre_parent_id")

    private Integer musicGenreParentId;
    @SerializedName("music_genre_name")

    private String musicGenreName;
    @SerializedName("music_genre_name_extended")

    private String musicGenreNameExtended;
    @SerializedName("music_genre_vanity")

    private String musicGenreVanity;

    public Integer getMusicGenreId() {
        return musicGenreId;
    }

    public void setMusicGenreId(Integer musicGenreId) {
        this.musicGenreId = musicGenreId;
    }

    public Integer getMusicGenreParentId() {
        return musicGenreParentId;
    }

    public void setMusicGenreParentId(Integer musicGenreParentId) {
        this.musicGenreParentId = musicGenreParentId;
    }

    public String getMusicGenreName() {
        return musicGenreName;
    }

    public void setMusicGenreName(String musicGenreName) {
        this.musicGenreName = musicGenreName;
    }

    public String getMusicGenreNameExtended() {
        return musicGenreNameExtended;
    }

    public void setMusicGenreNameExtended(String musicGenreNameExtended) {
        this.musicGenreNameExtended = musicGenreNameExtended;
    }

    public String getMusicGenreVanity() {
        return musicGenreVanity;
    }

    public void setMusicGenreVanity(String musicGenreVanity) {
        this.musicGenreVanity = musicGenreVanity;
    }
}

package com.gmantovi.harmony.gsonClasses.album;

import com.google.gson.annotations.SerializedName;

public class MusicGenreList {
    @SerializedName("music_genre")
    private MusicGenre musicGenre;

    public MusicGenre getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(MusicGenre musicGenre) {
        this.musicGenre = musicGenre;
    }
}

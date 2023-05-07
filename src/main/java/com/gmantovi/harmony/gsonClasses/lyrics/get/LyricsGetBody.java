package com.gmantovi.harmony.gsonClasses.lyrics.get;

import com.gmantovi.harmony.gsonClasses.lyrics.Lyrics;
import com.google.gson.annotations.SerializedName;

public class LyricsGetBody {
	
    @SerializedName("lyrics")
    private Lyrics lyrics;

    public void setLyrics(Lyrics lyrics) {
        this.lyrics = lyrics;
    }

    public Lyrics getLyrics() {
        return lyrics;
    }
}

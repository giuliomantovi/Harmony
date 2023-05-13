package com.gmantovi.harmony.gsonClasses.artist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistData {

    @SerializedName("artist_id") private Integer artistId;
    @SerializedName("artist_name") private String artistName;
    @SerializedName("artist_rating") private Integer artistRating;
    @SerializedName("artist_country") private String artistCountry;
    @SerializedName("artist_comment") private String artistComment;
    @SerializedName("artist_alias_list") private List<Alias> aliasList;

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Integer getArtistRating() {
        return artistRating;
    }

    public void setArtistRating(Integer artistRating) {
        this.artistRating = artistRating;
    }

    public String getArtistCountry() {
        return artistCountry;
    }

    public void setArtistCountry(String artistCountry) {
        this.artistCountry = artistCountry;
    }

    public String getArtistComment() {
        return artistComment;
    }

    public void setArtistComment(String artistComment) {
        this.artistComment = artistComment;
    }

    public List<Alias> getAliasList() {return aliasList;}

    public void setAliasList(List<Alias> aliasList) {this.aliasList = aliasList;}
}

package com.gmantovi.harmony.gsonClasses.album;

import com.google.gson.annotations.SerializedName;

public class AlbumData {

    @SerializedName("album_id") private Integer albumId;
    @SerializedName("album_mbid") private String albumMbid;
    @SerializedName("album_name") private String albumName;
    @SerializedName("album_rating") private Integer albumRating;
    @SerializedName("album_release_date") private String albumReleaseDate;
    @SerializedName("artist_id") private Integer artistId;
    @SerializedName("artist_name") private String artistName;
    @SerializedName("primary_genres") private PrimaryGenres primaryGenres;
    @SerializedName("album_pline") private String albumPline;
    @SerializedName("album_copyright") private String albumCopyright;
    @SerializedName("album_label") private String albumLabel;
    @SerializedName("restricted") private Integer restricted;
    @SerializedName("updated_time") private String updatedTime;
    @SerializedName("album_coverart_100x100 ") private String albumCoverArt;

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumMbid() {
        return albumMbid;
    }

    public void setAlbumMbid(String albumMbid) {
        this.albumMbid = albumMbid;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getAlbumRating() {
        return albumRating;
    }

    public void setAlbumRating(Integer albumRating) {
        this.albumRating = albumRating;
    }

    public String getAlbumReleaseDate() {
        return albumReleaseDate;
    }

    public void setAlbumReleaseDate(String albumReleaseDate) {
        this.albumReleaseDate = albumReleaseDate;
    }

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

    public PrimaryGenres getPrimaryGenres() {
        return primaryGenres;
    }

    public void setPrimaryGenres(PrimaryGenres primaryGenres) {
        this.primaryGenres = primaryGenres;
    }

    public String getAlbumPline() {
        return albumPline;
    }

    public void setAlbumPline(String albumPline) {
        this.albumPline = albumPline;
    }

    public String getAlbumCopyright() {
        return albumCopyright;
    }

    public void setAlbumCopyright(String albumCopyright) {
        this.albumCopyright = albumCopyright;
    }

    public String getAlbumLabel() {
        return albumLabel;
    }

    public void setAlbumLabel(String albumLabel) {
        this.albumLabel = albumLabel;
    }

    public Integer getRestricted() {
        return restricted;
    }

    public void setRestricted(Integer restricted) {
        this.restricted = restricted;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getAlbumCoverArt() {
        return albumCoverArt;
    }

    public void setAlbumCoverArt(String albumCoverArt) {
        this.albumCoverArt = albumCoverArt;
    }
}

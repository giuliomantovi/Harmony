package com.gmantovi.harmony.API;

import com.gmantovi.harmony.gsonClasses.album.Album;
import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.lyrics.Lyrics;
import com.gmantovi.harmony.gsonClasses.snippet.Snippet;
import com.gmantovi.harmony.gsonClasses.track.Track;

import java.util.List;
import java.util.Map;

public interface MusixMatchAPI {
    Lyrics getLyrics(int trackID);
    Track getTrack(int trackID);
    Artist getArtist(int artistID);
    List<Artist> searchArtists(String q_artist, int page_size);
    List<Artist> getArtistsList(String country, int page_size, String chart_name, String method, int artist_ID);
    List<Album> getArtistAlbums(int artistID, int page_size);
    List<Track> getTracksChart(String country, int page_size, String chart_name);
    List<Track> getAlbumTracks(int albumID, int page_size);
    Snippet getSnippet(int trackID);
    Track getMatchingTrack(String q_track, String q_artist);
}

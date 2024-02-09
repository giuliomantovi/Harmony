/*
*GNU GENERAL PUBLIC LICENSE
*Version 3, 29 June 2007
*
* Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in read me
* Everyone is permitted to copy and distribute verbatim copies
* of this license document, but changing it is not allowed.
*/

package com.gmantovi.harmony.API;

import com.gmantovi.harmony.config.*;
import com.gmantovi.harmony.gsonClasses.album.Album;
import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.artist.ArtistData;
import com.gmantovi.harmony.gsonClasses.artist.get.ArtistGetMessage;
import com.gmantovi.harmony.gsonClasses.artist.getAlbums.AlbumsGetMessage;
import com.gmantovi.harmony.gsonClasses.artist.search.ArtistSearchMessage;
import com.gmantovi.harmony.gsonClasses.error.ErrorMessage;
import com.gmantovi.harmony.gsonClasses.lyrics.Lyrics;
import com.gmantovi.harmony.gsonClasses.lyrics.get.LyricsGetMessage;
import com.gmantovi.harmony.gsonClasses.snippet.Snippet;
import com.gmantovi.harmony.gsonClasses.snippet.get.SnippetGetMessage;
import com.gmantovi.harmony.gsonClasses.track.Track;
import com.gmantovi.harmony.gsonClasses.track.TrackData;
import com.gmantovi.harmony.gsonClasses.track.chart.TrackChartMessage;
import com.gmantovi.harmony.gsonClasses.track.get.TrackGetMessage;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class for getting all the music data needed from the API
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class Proxy implements MusixMatchAPI{
    /**
     * A musiXmatch API Key.
     */
    private final String apiKey;

    /**
     * A musiXmatch API Key.
     */
    private static Proxy proxy;

    /**
     * Proxy Constructor with API-Key.
     *
     * @param apiKey
     *            A musiXmatch API Key.
     */
    private Proxy(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Proxy method for singleton pattern.
     *
     * @param apiKey
     *            instace of class.
     */
    public synchronized static Proxy getInstance(String apiKey){
        if(proxy==null){
            proxy=new Proxy(apiKey);
        }
        return proxy;
    }
    /**
     * Get Lyrics for the specific trackID.
     *
     * @param trackID
     *          musixmatch API track identifier
     * @return Lyrics object
     */
    public Lyrics getLyrics(int trackID) {
        Lyrics lyrics = null;
        LyricsGetMessage message = null;
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_ID, "" + trackID);

        String response;
        response = SendRequest.sendRequest(URLStringBuilder.getURLString(
                Methods.TRACK_LYRICS_GET, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, LyricsGetMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }

        if (message != null) {
            int statusCode = message.getContainer().getHeader().getStatusCode();
            if (statusCode > 200) {
                throw new NoSuchElementException("Status Code is " + statusCode);
            }
            lyrics = message.getContainer().getBody().getLyrics();
        }
        return lyrics;
    }

    /**
     * Get the track details using the specified trackId.
     *
     * @param trackID
     *            track identifier in musiXmatch catalog
     * @return the track
     */
    public Track getTrack(int trackID){
        Track track;
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_ID, "" + trackID);
        track = getTrackResponse(Methods.TRACK_GET,params);
        return track;
    }

    /**
     * Get the artist details using the specified artistId.
     *
     * @param artistID
     *            artist identifier in musiXmatch catalog
     * @return the Artist
     */
    public Artist getArtist(int artistID){
        Artist artist;
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.ARTIST_ID, "" + artistID);
        artist = getArtistResponse(params);
        return artist;
    }

    /**
     * Search artists using the given criteria.
     *
     * @param q_artist
     *            search for text string among artist names
     * @param page_size
     *            specify number of items per result page
     * @return a list of artists.
     */
    public List<Artist> searchArtists(String q_artist, int page_size) {
        List<Artist> artistList = null;
        ArtistSearchMessage message = null;
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.QUERY_ARTIST, q_artist);
        params.put(Constants.PAGE_SIZE, page_size);

        String response;
        response = SendRequest.sendRequest(URLStringBuilder.getURLString(
                Methods.ARTIST_SEARCH, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, ArtistSearchMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }
        if (message != null) {
            int statusCode = message.getArtistMessage().getHeader().getStatusCode();
            if (statusCode > 200) {
                throw new NoSuchElementException("Status Code is " + statusCode);
            }
            artistList = message.getArtistMessage().getBody().getArtist_list();
        }
        return artistList;
    }

    /**
     * Search either for top artist of a country or related artists of a certain one, depending on the method field.
     *
     * @param country
     *            2 letters representing a country (only needed for the top artists method)
     * @param page_size
     *            specify number of items per result page
     * @param chart_name
     *            (only needed for the top artists method),
     *            could be one of the following:
     *            top : editorial chart
     *            hot : Most viewed lyrics in the last 2 hours
     *            mxmweekly : Most viewed lyrics in the last 7 days
     *            mxmweekly_new : Most viewed lyrics in the last 7 days limited to new releases only
     * @param method
     *            could be get_artist_chart or get_related_artists, depending on the api response needed
     * @param artist_ID
     *            Musix match identifier for an artist (only needed for the related artist method)
     * @return a list of artists.
     */
    public List<Artist> getArtistsList(String country, int page_size, String chart_name, String method, int artist_ID) {
        List<Artist> artistList = null;
        ArtistSearchMessage message = null;
        Map<String, Object> params = new HashMap<>();
        String methodParam;
        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.PAGE_SIZE, page_size);

        if(method.equals(Methods.CHART_ARTISTS_GET)){
            params.put(Constants.CHART_NAME, chart_name);
            params.put(Constants.COUNTRY, country);
            methodParam = Methods.CHART_ARTISTS_GET;
        }else{
            params.put(Constants.ARTIST_ID, artist_ID);
            methodParam = Methods.ARTIST_RELATED_GET;
        }
        String response;
        response = SendRequest.sendRequest(URLStringBuilder.getURLString(
                methodParam, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, ArtistSearchMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }
        if(message != null){
            int statusCode = message.getArtistMessage().getHeader().getStatusCode();
            if (statusCode > 200) {
                throw new NoSuchElementException("Status Code is " + statusCode);
            }
            artistList = message.getArtistMessage().getBody().getArtist_list();
        }
        return artistList;
    }

    /**
     * returns all the albums of an artist using the given criteria.
     *
     * @param artistID
     *            Musixmatch id of the artist
     * @param page_size
     *            specify number of items per result page
     * @return a list of albums.
     */
    public List<Album> getArtistAlbums(int artistID, int page_size) {
        List<Album> albumsList = null;
        AlbumsGetMessage message = null;
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.ARTIST_ID, artistID);
        params.put(Constants.PAGE_SIZE, page_size);
        params.put(Constants.S_RELEASE_DATE, "desc");

        String response;
        response = SendRequest.sendRequest(URLStringBuilder.getURLString(
                Methods.ARTIST_ALBUMS_GET, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, AlbumsGetMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }
        if(message != null){
            int statusCode = message.getAlbumsMessage().getHeader().getStatusCode();
            if (statusCode > 200) {
                throw new NoSuchElementException("Status Code is " + statusCode);
            }
            albumsList = message.getAlbumsMessage().getBody().getAlbums_list();
        }
        return albumsList;
    }

    /**
     * Search top tracks of a certain country using the given criteria.
     *
     * @param country
     *            2 letters representing a country
     * @param page_size
     *            specify number of items per result page
     * @param chart_name
     *            could be one of the following:
     *            top : editorial chart
     *            hot : Most viewed lyrics in the last 2 hours
     *            mxmweekly : Most viewed lyrics in the last 7 days
     *            mxmweekly_new : Most viewed lyrics in the last 7 days limited to new releases only
     * @return a list of tracks.
     */
    public List<Track> getTracksChart(String country, int page_size, String chart_name) {
        List<Track> trackList = null;
        TrackChartMessage message = null;
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.COUNTRY, country);
        params.put(Constants.PAGE_SIZE, page_size);
        params.put(Constants.CHART_NAME, chart_name);

        String response;
        response = SendRequest.sendRequest(URLStringBuilder.getURLString(
                Methods.CHART_TRACKS_GET, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, TrackChartMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }
        if( message != null){
            int statusCode = message.getTrackChartMessage().getHeader().getStatusCode();
            if (statusCode > 200) {
                throw new NoSuchElementException("Status Code is " + statusCode);
            }

            trackList = message.getTrackChartMessage().getBody().getTrack_list();
        }
        return trackList;
    }

    /**
     * returns all the tracks of a specified album.
     *
     * @param albumID
     *            Musixmatch album id
     * @param page_size
     *            specify number of items per result page
     * @return a list of tracks.
     */
    public List<Track> getAlbumTracks(int albumID, int page_size) {
        List<Track> trackList = null;
        TrackChartMessage message = null;
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.ALBUM_ID, albumID);
        params.put(Constants.PAGE_SIZE, page_size);

        String response;
        response = SendRequest.sendRequest(URLStringBuilder.getURLString(
                Methods.ALBUM_TRACKS_GET, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, TrackChartMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }
        if(message != null){
            int statusCode = message.getTrackChartMessage().getHeader().getStatusCode();
            if (statusCode > 200) {
                throw new NoSuchElementException("Status Code is " + statusCode);
            }
            trackList = message.getTrackChartMessage().getBody().getTrack_list();
        }
        return trackList;
    }

    /**
     * Get Snippet (brief track description) for the specified trackID.
     *
     * @param trackID
     *          musixmatch api track identifier
     *
     * @return Snippet Object
     */

    public Snippet getSnippet(int trackID){
        Snippet snippet = null;
        SnippetGetMessage message = null;
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_ID, "" + trackID);

        String response;
        response = SendRequest.sendRequest(URLStringBuilder.getURLString(
                Methods.TRACK_SNIPPET_GET, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, SnippetGetMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }
        if(message != null){
            int statusCode = message.getContainer().getHeader().getStatusCode();
            if (statusCode > 200) {
                throw new NoSuchElementException("Status Code is " + statusCode);
            }
            snippet = message.getContainer().getBody().getSnippet();
        }
        return snippet;
    }

    /**
     * Get the most matching track which was retrieved using the search.
     *
     * @param q_track
     *            search for text string among track names
     * @param q_artist
     *            search for text string among artist names
     * @return the track
     */
    public Track getMatchingTrack(String q_track, String q_artist) {
        Track track;
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.QUERY_TRACK, q_track);
        params.put(Constants.QUERY_ARTIST, q_artist);

        track = getTrackResponse(Methods.MATCHER_TRACK_GET, params);
        return track;
    }

    /**
     * Returns the track response which was returned through the query (needed for getTrack and getmatchingTrack methods).
     *
     * @param methodName
     *            the name of the API method.
     * @param params
     *            a map which contains the key-value pair
     * @return the track details.
     */
    private Track getTrackResponse(String methodName, Map<String, Object> params) {
        Track track = new Track();
        String response;
        TrackGetMessage message;

        response = SendRequest.sendRequest(URLStringBuilder.getURLString(
                methodName, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, TrackGetMessage.class);
            if(message != null){
                int statusCode = message.getTrackMessage().getHeader().getStatusCode();
                if (statusCode > 200) {
                    throw new NoSuchElementException("Status Code is " + statusCode);
                }
                TrackData data = message.getTrackMessage().getBody().getTrack();
                track.setTrack(data);
            }
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return track;
    }

    /**
     * Returns the artist response which was returned through the query.
     *
     * @param params a map which contains the key-value pair
     * @return the album details.
     */
    private Artist getArtistResponse(Map<String, Object> params) {
        Artist artist = new Artist();
        String response;
        ArtistGetMessage message;

        response = SendRequest.sendRequest(URLStringBuilder.getURLString(
                Methods.ARTIST_GET, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, ArtistGetMessage.class);
            if(message != null) {
                int statusCode = message.getArtistMessage().getHeader().getStatusCode();
                if (statusCode > 200) {
                    throw new NoSuchElementException("Status Code is " + statusCode);
                }
                ArtistData data = message.getArtistMessage().getBody().getArtist();
                artist.setArtist(data);
            }
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return artist;
    }

    /**
     * Handle the error response.
     *
     * @param jsonResponse
     *            the jsonContent.
     *             if any error occurs
     */
    private void handleErrorResponse(String jsonResponse) {
        StatusCode statusCode;
        Gson gson = new Gson();

        ErrorMessage errMessage = gson.fromJson(jsonResponse,
                ErrorMessage.class);
        int responseCode = errMessage.getMessageContainer().getHeader()
                .getStatusCode();

        statusCode = switch (responseCode) {
            case 400 -> StatusCode.BAD_SYNTAX;
            case 401 -> StatusCode.AUTH_FAILED;
            case 402 -> StatusCode.LIMIT_REACHED;
            case 403 -> StatusCode.NOT_AUTHORIZED;
            case 404 -> StatusCode.RESOURCE_NOT_FOUND;
            case 405 -> StatusCode.METHOD_NOT_FOUND;
            default -> StatusCode.ERROR;
        };

        throw new NullPointerException("STATUS CODE: "+statusCode.getStatusCode()+", "+statusCode.getStatusMessage());
    }

    /*
    public Album getAlbum(int albumID){
        Album album = new Album();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.ALBUM_ID, "" + albumID);

        album = getAlbumResponse(Methods.ALBUM_GET, params);

        return album;

    private Album getAlbumResponse(String methodName, Map<String, Object> params) {
        Album album = new Album();
        String response = null;
        AlbumGetMessage message = null;

        response = SendRequest.sendRequest(SendRequest.getURLString(
                methodName, params));

        try {
            Gson gson = new Gson();
            message = gson.fromJson(response, AlbumGetMessage.class);
            if(message != null) {
                int statusCode = message.getAlbumMessage().getHeader().getStatusCode();
                if (statusCode > 200) {
                    throw new NoSuchElementException("Status Code is " + statusCode);
                }
                AlbumData data = message.getAlbumMessage().getBody().getAlbum();
                album.setAlbum(data);
            }
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return album;
    }*/
}

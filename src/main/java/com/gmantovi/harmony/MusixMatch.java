package com.gmantovi.harmony;

import com.gmantovi.harmony.config.Constants;
import com.gmantovi.harmony.config.Methods;
import com.gmantovi.harmony.config.StatusCode;
import com.gmantovi.harmony.gsonClasses.album.Album;
import com.gmantovi.harmony.gsonClasses.album.AlbumData;
import com.gmantovi.harmony.gsonClasses.album.get.AlbumGetMessage;
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

public class MusixMatch {
    /**
     * A musiXmatch API Key.
     */
    private final String apiKey;

    /**
     * MusixMatch Constructor with API-Key.
     *
     * @param apiKey
     *            A musiXmatch API Key.
     */
    public MusixMatch(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Get Lyrics for the specific trackID.
     *
     * @param trackID
     * @return
     */
    public Lyrics getLyrics(int trackID) {
        Lyrics lyrics = null;
        LyricsGetMessage message = null;
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_ID, "" + trackID);

        String response;
        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                Methods.TRACK_LYRICS_GET, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, LyricsGetMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }

        try {
            lyrics = message.getContainer().getBody().getLyrics();
        }catch (NullPointerException e){
            e.printStackTrace();
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
        Track track = new Track();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_ID, "" + trackID);

        /*TrackGetMessage message = new TrackGetMessage();
        TrackData data = new TrackData();
        ElementResponse<Track,TrackGetMessage,TrackData> e = new ElementResponse<>(new Track(),TrackGetMessage.class, message, data);
        track = e.getElementResponse(Methods.TRACK_GET, params,
                m -> m.getTrackMessage.getBody().getTrack(),
                t -> nomeTrack.setTrack(t));
        */
        track = getTrackResponse(Methods.TRACK_GET,params);
        return track;
    }

    /**
     * Get the album details using the specified albumId.
     *
     * @param albumID
     *            album identifier in musiXmatch catalog
     * @return the album
     */
    public Album getAlbum(int albumID){
        Album album = new Album();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.ALBUM_ID, "" + albumID);

        album = getAlbumResponse(Methods.ALBUM_GET, params);

        return album;
    }

    /**
     * Get the artist details using the specified artistId.
     *
     * @param artistID
     *            artist identifier in musiXmatch catalog
     * @return the album
     */
    public Artist getArtist(int artistID){
        Artist artist = new Artist();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.ARTIST_ID, "" + artistID);

        artist = getArtistResponse(Methods.ARTIST_GET, params);

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
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.QUERY_ARTIST, q_artist);
        params.put(Constants.PAGE_SIZE, page_size);

        String response = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                Methods.ARTIST_SEARCH, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, ArtistSearchMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }

        int statusCode = message.getArtistMessage().getHeader().getStatusCode();

        if (statusCode > 200) {
            throw new NoSuchElementException("Status Code is not 200");
        }

        artistList = message.getArtistMessage().getBody().getArtist_list();

        return artistList;
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
     * @return a list of artists.
     */
    public List<Artist> getArtistsChart(String country, int page_size, String chart_name) {
        List<Artist> artistList = null;
        ArtistSearchMessage message = null;
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.COUNTRY, country);
        params.put(Constants.PAGE_SIZE, page_size);
        params.put(Constants.CHART_NAME, chart_name);

        String response = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                Methods.CHART_ARTISTS_GET, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, ArtistSearchMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }

        int statusCode = message.getArtistMessage().getHeader().getStatusCode();

        if (statusCode > 200) {
            throw new NoSuchElementException("Status Code is not 200");
        }

        artistList = message.getArtistMessage().getBody().getArtist_list();

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
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.ARTIST_ID, artistID);
        params.put(Constants.PAGE_SIZE, page_size);
        params.put(Constants.S_RELEASE_DATE, "desc");

        String response = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                Methods.ARTIST_ALBUMS_GET, params));
        System.out.println("RISPOSTA GSON = " + response);
        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, AlbumsGetMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }

        int statusCode = message.getAlbumsMessage().getHeader().getStatusCode();

        if (statusCode > 200) {
            throw new NoSuchElementException("Status Code is not 200");
        }

        albumsList = message.getAlbumsMessage().getBody().getAlbums_list();

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
     * @return a list of artists.
     */
    public List<Track> getTracksChart(String country, int page_size, String chart_name) {
        List<Track> trackList = null;
        TrackChartMessage message = null;
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.COUNTRY, country);
        params.put(Constants.PAGE_SIZE, page_size);
        params.put(Constants.CHART_NAME, chart_name);

        String response = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                Methods.CHART_TRACKS_GET, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, TrackChartMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }

        int statusCode = message.getTrackChartMessage().getHeader().getStatusCode();

        if (statusCode > 200) {
            throw new NoSuchElementException("Status Code is not 200");
        }

        trackList = message.getTrackChartMessage().getBody().getTrack_list();

        return trackList;
    }

    /**
     * Search top tracks of a certain country using the given criteria.
     *
     * @param albumID
     *            Musixmatch album id
     * @param page_size
     *            specify number of items per result page
     * @return a list of artists.
     */
    public List<Track> getAlbumTracks(int albumID, int page_size) {
        List<Track> trackList = null;
        TrackChartMessage message = null;
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.ALBUM_ID, albumID);
        params.put(Constants.PAGE_SIZE, page_size);

        String response = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                Methods.ALBUM_TRACKS_GET, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, TrackChartMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }

        int statusCode = message.getTrackChartMessage().getHeader().getStatusCode();

        if (statusCode > 200) {
            throw new NoSuchElementException("Status Code is not 200");
        }

        trackList = message.getTrackChartMessage().getBody().getTrack_list();

        return trackList;
    }

    /**
     * Get Snippet for the specified trackID.
     * @param trackID
     * @return
     */

    public Snippet getSnippet(int trackID){
        Snippet snippet = null;
        SnippetGetMessage message = null;
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_ID, "" + trackID);

        String response = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                Methods.TRACK_SNIPPET_GET, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, SnippetGetMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }
        snippet = message.getContainer().getBody().getSnippet();

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
        Track track = new Track();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.QUERY_TRACK, q_track);
        params.put(Constants.QUERY_ARTIST, q_artist);

        track = getTrackResponse(Methods.MATCHER_TRACK_GET, params);

        return track;
    }

    /**
     * Returns the track response which was returned through the query.
     *
     * @param methodName
     *            the name of the API method.
     * @param params
     *            a map which contains the key-value pair
     * @return the track details.
     *             if any error occurs.
     */
    private Track getTrackResponse(String methodName, Map<String, Object> params) {
        Track track = new Track();
        String response = null;
        TrackGetMessage message = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                methodName, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, TrackGetMessage.class);
            TrackData data = message.getTrackMessage().getBody().getTrack();
            track.setTrack(data);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }

        return track;
    }

    /**
     * Returns the album response which was returned through the query.
     *
     * @param methodName
     *            the name of the API method.
     * @param params
     *            a map which contains the key-value pair
     * @return the album details.
     *             if any error occurs.
     */
    private Album getAlbumResponse(String methodName, Map<String, Object> params) {
        Album album = new Album();
        String response = null;
        AlbumGetMessage message = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                methodName, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, AlbumGetMessage.class);
            AlbumData data = message.getAlbumMessage().getBody().getAlbum();
            album.setAlbum(data);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }

        return album;
    }

    /**
     * Returns the artist response which was returned through the query.
     *
     * @param methodName
     *            the name of the API method.
     * @param params
     *            a map which contains the key-value pair
     * @return the album details.
     *             if any error occurs.
     */
    private Artist getArtistResponse(String methodName, Map<String, Object> params) {
        Artist artist = new Artist();
        String response = null;
        ArtistGetMessage message = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                methodName, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, ArtistGetMessage.class);
            ArtistData data = message.getArtistMessage().getBody().getArtist();
            artist.setArtist(data);
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

        System.out.println("STATUS CODE: "+statusCode.getStatusCode()+", "+statusCode.getStatusMessage());
        throw new NullPointerException();
    }
}

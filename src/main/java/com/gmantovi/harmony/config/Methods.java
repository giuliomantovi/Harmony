/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in README
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.config;
/**
 * A class to hold the API methods.
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class Methods {
    /**
     * Get an album from our database: name, release_date, release_type, cover
     * art.
     */
    public static final String ALBUM_GET = "album.get";

    /**
     * Get the list of all the tracks of an album.
     */
    public static final String ALBUM_TRACKS_GET = "album.tracks.get";

    /**
     * Get the discography of an artist.
     */
    public static final String ARTIST_ALBUMS_GET = "artist.albums.get";

    /**
     * This api provides you the list of the top artists of a given country.
     */
    public static final String CHART_ARTISTS_GET = "chart.artists.get";

    /**
     * Get the artist data from our database.
     */
    public static final String ARTIST_GET = "artist.get";

    /**
     * Get related artists for a certain artist.
     */
    public static final String ARTIST_RELATED_GET = "artist.related.get";


    /**
     * Search for artists in our database.
     */
    public static final String ARTIST_SEARCH = "artist.search";

    /**
     * Match your track against our database.
     */
    public static final String MATCHER_TRACK_GET = "matcher.track.get";

    /**
     * With this api you'll be able to get the base url for the tracking script
     * you need to insert in your page to legalize your existent lyrics library.
     */
    public static final String TRACKING_URL_GET = "tracking.url.get";

    /**
     * This api provides you the list of the top tracks of the supported
     * countries.
     */
    public static final String CHART_TRACKS_GET = "chart.tracks.get";

    /**
     * Get a track info from our database: title, artist, instrumental flag and
     * cover art.
     */
    public static final String TRACK_GET = "track.get";

    /**
     * Retrieve the lyrics of a track.
     */
    public static final String TRACK_LYRICS_GET = "track.lyrics.get";

    /**
     * Retrieve the lyric snippet of a track.
     */
    public static final String TRACK_SNIPPET_GET = "track.snippet.get";

}

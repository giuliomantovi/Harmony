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
 * A class to hold the Constants values.
 * @author Giulio Mantovi
 * @version 1.0
 */
public final class Constants {
    /**
     * A constant field to denote the value of the url to connect to my local mysql database.
     */
    public static final String MYSQL_CONNECTION_URL = "jdbc:mysql://localhost/harmony?user=root&password=";

    /**
     * A constant field to denote the value of my personal APIKEY.
     */
    public static final String PERSONAL_API_KEY = "391689594f1ad1d992b2efd5fc5862ef";

    /**
     * A constant field to denote the value of API_KEY in the KV mapping.
     */
    public static final String API_KEY = "apikey";

    /**
     * A constant field to denote the base API url.
     */
    public static final String API_URL = "http://api.musixmatch.com/ws/";

    /**
     * A constant field to denote the API version.
     */
    public static final String API_VERSION = "1.1";

    /**
     * A constant field to denote the value of FORMAT in the KV mapping.
     */
    public static final String FORMAT = "format";

    /**
     * A constant field to denote the value of F_HAS_LYRICS in the KV mapping.
     */
    public static final String F_HAS_LYRICS = "f_has_lyrics";

    /**
     * A constant field to denote the value of JSON in the KV mapping.
     */
    public static final String JSON = "json";

    /**
     * A constant field to denote the value of PAGE in the KV mapping.
     */
    public static final String PAGE = "page";

    /**
     * A constant field to denote the value of PAGE_SIZE in the KV mapping.
     */
    public static final String PAGE_SIZE = "page_size";

    /**
     * A constant field to denote the value of COUNTRY in the KV mapping.
     */
    public static final String COUNTRY = "country";

    /**
     * A constant field to denote the value of COUNTRY in the KV mapping.
     */
    public static final String CHART_NAME = "chart_name";

    /**
     * A constant field to denote the value of QUERY in the KV mapping.
     */
    public static final String QUERY = "q";

    /**
     * A constant field to denote the value of QUERY_ARTIST in the KV mapping.
     */
    public static final String QUERY_ARTIST = "q_artist";

    /**
     * A constant field to denote the value of QUERY_TRACK in the KV mapping.
     */
    public static final String QUERY_TRACK = "q_track";

    /**
     * A constant field to denote the value of TRACK_ID in the KV mapping.
     */
    public static final String TRACK_ID = "track_id";

    /**
     * A constant field to denote the value of ALBUM_ID in the KV mapping.
     */
    public static final String ALBUM_ID = "album_id";

    /**
     * A constant field to denote the value of ARTIST_ID in the KV mapping.
     */
    public static final String ARTIST_ID = "artist_id";

    /**
     * A constant field to denote the value of S_RELEASE_DATE in the KV mapping.
     */
    public static final String S_RELEASE_DATE = "s_release_date";

    /**
     * A constant field to denote the value of '/' .
     */
    public static final String URL_DELIM = "/";

    /**
     * A constant field to denote the value of XML in the KV mapping.
     */
    public static final String XML = "xml";
}

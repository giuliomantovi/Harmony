/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in read me
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */

package com.gmantovi.harmony;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Class for building the URL request and sending it
 */
public class MusixMatchRequest {

    /**
     * API url, last 2 digits represent the version number.
     */
    public static final String API_URL = "https://api.musixmatch.com/ws/1.1/";

    /**
     * This method is used to get json responses from the API
     *
     * @param requestString
     *            String containing API method name and call parameters, properly formatted for the URL
     * @return a string containing the json response from the API
     */
    public static String sendRequest(String requestString) {
        StringBuilder buffer = new StringBuilder();
        try {
            String apiUrl = API_URL + requestString;
            URL url = new URL(apiUrl);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    url.openStream(), StandardCharsets.UTF_8));

            String str;
            while ((str = in.readLine()) != null) {
                buffer.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * This method is used to get a parameter string from the Map.
     *
     * @param params
     *            key-value parameters
     * @return A String containing the url parameter for the API call.
     */
    public static String getURLString(String methodName, Map<String, Object> params) {
        StringBuilder paramString = new StringBuilder();
        paramString.append(methodName).append("?");

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            paramString.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),
                    StandardCharsets.UTF_8));
            paramString.append("&");
        }

        paramString = new StringBuilder(paramString.substring(0, paramString.length() - 1));
        return paramString.toString();
    }
}

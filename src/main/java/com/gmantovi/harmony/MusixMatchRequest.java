package com.gmantovi.harmony;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
public class MusixMatchRequest {
    /**
     * Declares API_KEY value for the k-v mapping format.
     */
    public static final String API_KEY = "apikey";
    /**
     * Declares my MusixMatchAPI personal API key for the requests.
     */
    public static final String MY_API_KEY = "391689594f1ad1d992b2efd5fc5862ef";
    /**
     * API url, last 2 digits represent the version number.
     */
    public static final String API_URL = "https://api.musixmatch.com/ws/1.1/";

    public static String sendRequest(String requestString) {
        StringBuilder buffer = new StringBuilder();
        try {
            String apiUrl = API_URL + requestString;
            System.out.println("STRINGA RICHIESTA = " + apiUrl);
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

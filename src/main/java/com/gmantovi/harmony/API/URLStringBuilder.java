package com.gmantovi.harmony.API;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class URLStringBuilder {
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

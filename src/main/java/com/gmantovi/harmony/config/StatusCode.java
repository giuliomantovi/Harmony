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
 * A class to hold the status codes.
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class StatusCode {
    /**
     * Status Code - 200
     * <p/>
     * The request was successful
     */
    public static final StatusCode REQ_SUCCESS =
            new StatusCode(200, "The request was successful.");

    /**
     * Status Code - 403
     * <p/>
     * You are not authorized to perform this operation / the api version you�re
     * trying to use has been shut down.
     */
    public static final StatusCode NOT_AUTHORIZED =
            new StatusCode(
                    403,
                    "You are not authorized to perform this operation / the api version you�re trying to use has been shut down.");

    /**
     * Status Code - 402
     * <p/>
     * a limit was reached, either you exceeded per hour requests limits or your
     * balance is insufficient.
     */
    public static final StatusCode LIMIT_REACHED =
            new StatusCode(
                    402,
                    "a limit was reached, either you exceeded per hour requests limits or your balance is insufficient.");

    /**
     * Status Code - 400
     * <p/>
     * The request had bad syntax or was inherently impossible to be satisfied
     */
    public static final StatusCode BAD_SYNTAX =
            new StatusCode(
                    400,
                    "The request had bad syntax or was inherently impossible to be satisfied");

    /**
     * Status Code - 401
     * <p/>
     * The request had bad syntax or was inherently impossible to be satisfied
     */
    public static final StatusCode AUTH_FAILED =
            new StatusCode(
                    401,
                    "The request had bad syntax or was inherently impossible to be satisfied");

    /**
     * Status Code - 404
     * <p/>
     * requested resource was not found
     */
    public static final StatusCode RESOURCE_NOT_FOUND =
            new StatusCode(404, "requested resource was not found");

    /**
     * Status Code - 405
     * <p/>
     * requested method was not found
     */
    public static final StatusCode METHOD_NOT_FOUND =
            new StatusCode(404, "requested method was not found");

    /**
     * An error which has not been defined in the API
     */
    public static final StatusCode ERROR =
            new StatusCode(
                    999,
                    "An error has occured while performing the query. Please contact the administrator.");
    private int statusCode;
    private String statusMessage;

    private StatusCode(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    /**
     * @return the statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return the statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }
}

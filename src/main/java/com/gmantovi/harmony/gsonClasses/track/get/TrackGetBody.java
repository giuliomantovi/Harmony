/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in README
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.track.get;

import com.gmantovi.harmony.gsonClasses.track.TrackData;
import com.google.gson.annotations.SerializedName;

/**
 * A class to denote the body part of the JSON.
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public class TrackGetBody {
    @SerializedName("track")
    private TrackData track;

    public void setTrack(TrackData track) {
        this.track = track;
    }

    public TrackData getTrack() {
        return track;
    }
}

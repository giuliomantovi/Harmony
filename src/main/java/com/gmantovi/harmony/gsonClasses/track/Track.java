/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in README
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.track;

import com.google.gson.annotations.SerializedName;

/**
 * A class to denote the track entity.
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public class Track {
    @SerializedName("track")
    private TrackData track;

    public void setTrack(TrackData track) {
        this.track = track;
    }

    public TrackData getTrack() {
        return track;
    }
}

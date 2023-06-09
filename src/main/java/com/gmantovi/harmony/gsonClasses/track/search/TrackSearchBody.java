/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in README
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.track.search;

import com.gmantovi.harmony.gsonClasses.track.Track;

import java.util.List;

/**
 * A class to denote the body part of the JSON.
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public class TrackSearchBody {
    private List<Track> track_list;

    public void setTrack_list(List<Track> track_list) {
        this.track_list = track_list;
    }

    public List<Track> getTrack_list() {
        return track_list;
    }
}

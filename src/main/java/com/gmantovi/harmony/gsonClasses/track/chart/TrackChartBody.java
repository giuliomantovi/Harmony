/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.track.chart;


import com.gmantovi.harmony.gsonClasses.track.Track;

import java.util.List;
/**
 * A class for json data retrieval.
 *
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class TrackChartBody {
    private List<Track> track_list;

    public void setTrack_list(List<Track> track_list) {
        this.track_list = track_list;
    }

    public List<Track> getTrack_list() {
        return track_list;
    }
}
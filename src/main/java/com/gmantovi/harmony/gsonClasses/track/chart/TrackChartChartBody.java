package com.gmantovi.harmony.gsonClasses.track.chart;


import com.gmantovi.harmony.gsonClasses.track.Track;

import java.util.List;

public class TrackChartChartBody {
    private List<Track> track_list;

    public void setTrack_list(List<Track> track_list) {
        this.track_list = track_list;
    }

    public List<Track> getTrack_list() {
        return track_list;
    }
}
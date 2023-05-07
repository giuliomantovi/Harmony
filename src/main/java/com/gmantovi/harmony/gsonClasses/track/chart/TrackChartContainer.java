package com.gmantovi.harmony.gsonClasses.track.chart;

import com.gmantovi.harmony.gsonClasses.Header;
import com.google.gson.annotations.SerializedName;

public class TrackChartContainer {

    @SerializedName("header")
    private Header header;


    @SerializedName("body")
    private TrackChartChartBody body;


    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public TrackChartChartBody getBody() {
        return body;
    }

    public void setBody(TrackChartChartBody body) {
        this.body = body;
    }
}

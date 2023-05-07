package com.gmantovi.harmony.gsonClasses.track.get;

import com.gmantovi.harmony.gsonClasses.Header;
import com.google.gson.annotations.SerializedName;

public class TrackGetContainer {

    @SerializedName("header")
    private Header header;


    @SerializedName("body")
    private TrackGetBody body;


    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public TrackGetBody getBody() {
        return body;
    }

    public void setBody(TrackGetBody body) {
        this.body = body;
    }
}

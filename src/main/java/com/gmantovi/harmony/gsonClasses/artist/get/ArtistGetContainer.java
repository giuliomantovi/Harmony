package com.gmantovi.harmony.gsonClasses.artist.get;

import com.gmantovi.harmony.gsonClasses.Header;
import com.google.gson.annotations.SerializedName;

public class ArtistGetContainer {

    @SerializedName("header")
    private Header header;


    @SerializedName("body")
    private ArtistGetBody body;


    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ArtistGetBody getBody() {
        return body;
    }

    public void setBody(ArtistGetBody body) {
        this.body = body;
    }
}

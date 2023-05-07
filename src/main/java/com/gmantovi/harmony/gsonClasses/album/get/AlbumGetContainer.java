package com.gmantovi.harmony.gsonClasses.album.get;

import com.gmantovi.harmony.gsonClasses.Header;
import com.google.gson.annotations.SerializedName;

public class AlbumGetContainer {

    @SerializedName("header")
    private Header header;

    @SerializedName("body")
    private AlbumGetBody body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AlbumGetBody getBody() {
        return body;
    }

    public void setBody(AlbumGetBody body) {
        this.body = body;
    }
}

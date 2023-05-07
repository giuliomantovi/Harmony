package com.gmantovi.harmony.gsonClasses.artist.getAlbums;

import com.gmantovi.harmony.gsonClasses.Header;
import com.google.gson.annotations.SerializedName;

public class AlbumsGetContainer {

    @SerializedName("header")
    private Header header;

    @SerializedName("body")
    private AlbumsGetBody body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AlbumsGetBody getBody() {
        return body;
    }

    public void setBody(AlbumsGetBody body) {
        this.body = body;
    }
}

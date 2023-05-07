package com.gmantovi.harmony.gsonClasses.lyrics.get;

import com.gmantovi.harmony.gsonClasses.Header;
import com.google.gson.annotations.SerializedName;

public class LyricsGetContainer {
	
    @SerializedName("body")
    private LyricsGetBody body;
    
    @SerializedName("header")
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public LyricsGetBody getBody() {
        return body;
    }

    public void setBody(LyricsGetBody body) {
        this.body = body;
    }
}

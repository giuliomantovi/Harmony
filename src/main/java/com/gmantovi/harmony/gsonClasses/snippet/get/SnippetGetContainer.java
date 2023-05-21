/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in README
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.snippet.get;

import com.gmantovi.harmony.gsonClasses.Header;
import com.google.gson.annotations.SerializedName;
/**
 * class for json data retrieval.
 * @author Sachin handiekar
 * @version 1.0
 */
public class SnippetGetContainer {

    @SerializedName("body")
    private SnippetGetBody body;

    @SerializedName("header")
    private Header header;

    public SnippetGetBody getBody() {
        return body;
    }

    public void setBody(SnippetGetBody body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}

/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in README
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.snippet;

import com.google.gson.annotations.SerializedName;

/**
 * Objects of this class represent a lyric snippet from the
 * MusixMatchAPI API. Code based on existing jMusixMatch classes.
 * @author Sachin handiekar
 * @version 1.0
 */
public class Snippet {

    @SerializedName("snippet_language")
    private String snippetLanguage;

    @SerializedName("snippet_body")
    private String snippetBody;

    public String getSnippetLanguage() {
        return snippetLanguage;
    }

    public void setSnippetLanguage(String snippetLanguage) {
        this.snippetLanguage = snippetLanguage;
    }

    public String getSnippetBody() {
        return snippetBody;
    }

    public void setSnippetBody(String snippetBody) {
        this.snippetBody = snippetBody;
    }

}

package com.gmantovi.harmony.gsonClasses.snippet;

import com.google.gson.annotations.SerializedName;

/**
 * Objects of this class represent a lyric snippet from the
 * MusixMatch API. Code based on existing jMusixMatch classes.
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

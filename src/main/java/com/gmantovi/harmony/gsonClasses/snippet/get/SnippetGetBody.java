package com.gmantovi.harmony.gsonClasses.snippet.get;

import com.gmantovi.harmony.gsonClasses.snippet.Snippet;
import com.google.gson.annotations.SerializedName;

public class SnippetGetBody {

    @SerializedName("snippet")
    private Snippet snippet;

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
}

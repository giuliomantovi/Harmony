package com.gmantovi.harmony.gsonClasses.artist;

import com.google.gson.annotations.SerializedName;

public class Alias {
    @SerializedName("artist_alias")
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi, contributed by sachin handiekar and others, full credits in README
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.gsonClasses.lyrics.get;

import com.google.gson.annotations.SerializedName;
/**
 * class for json data retrieval.
 * @author Sachin handiekar
 * @version 1.0
 */
public class LyricsGetMessage {
    @SerializedName("message")
    private LyricsGetContainer container;

    public void setContainer(LyricsGetContainer container) {
        this.container = container;
    }

    public LyricsGetContainer getContainer() {
        return container;
    }
}

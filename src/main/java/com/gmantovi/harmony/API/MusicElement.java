/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.API;

import java.util.Objects;

/**
 * MusicElement representing a track or an artist in the GUI components
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class MusicElement {
    private Integer id;
    private String name;
    private String type;
    private String authorName;
    /**
     * MusicElement Constructor for artists
     *
     * @param id  A musiXmatch ID for the artist.
     * @param name  artist name.
     * @param type  Could be 'track' or 'artist'.
     */
    public MusicElement(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    /**
     * MusicElement Constructor for tracks
     *
     * @param authorName Name of the singer
     */
    public MusicElement(Integer id, String name, String type, String authorName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.authorName = authorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicElement musicElement = (MusicElement) o;
        return id.equals(musicElement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

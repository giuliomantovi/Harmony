/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony;

/**
 * Element representing a track or an artist in the GUI components
 */
public class Element {
    private Integer id;
    private String name;
    private String type;
    private String authorName;

    public Element(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Element(Integer id, String name, String type, String authorName) {
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
}

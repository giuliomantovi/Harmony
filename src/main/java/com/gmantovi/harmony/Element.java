package com.gmantovi.harmony;

public class Element {
    private Integer id;
    private String name;
    private String type;
    //private String authorName;
    //private Integer authorID;

    public Element() { this(null,null,null); }

    public Element(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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
}

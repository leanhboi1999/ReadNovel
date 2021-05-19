package com.example.readnovel.model;

public class Banner {
    private String name;
    private String thumb;

    public Banner(String name, String thumb) {
        this.name = name;
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}

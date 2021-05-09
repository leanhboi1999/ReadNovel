package com.example.readnovel.model;

public class Search {
    private String name;
    private String thumb;
    private String url;

    public Search(String name, String thumb, String url) {
        this.name = name;
        this.thumb = thumb;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

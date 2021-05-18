package com.example.readnovel.Model;

import io.realm.RealmObject;

public class ComicDatabase extends RealmObject {
    private String name;
    private String url;
    private String thumbal;
    private String chapter;
    private String view;

    public ComicDatabase() {
    }

    public ComicDatabase(String name, String url, String thumbal, String chapter, String view) {
        this.name = name;
        this.url = url;
        this.thumbal = thumbal;
        this.chapter = chapter;
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumbal;
    }

    public void setThumb(String thumb) {
        this.thumbal = thumb;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}


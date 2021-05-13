package com.example.readnovel.model;

public class Comic {
    private String name;
    private String view;
    private String thumb;
    private String chapter;
    private String linkComic;

    public Comic(String name, String view, String thumb, String chapter, String linkComic) {
        this.name = name;
        this.view = view;
        this.thumb = thumb;
        this.chapter = chapter;
        this.linkComic = linkComic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getLinkComic() {
        return linkComic;
    }

    public void setLinkComic(String linkComic) {
        this.linkComic = linkComic;
    }
}

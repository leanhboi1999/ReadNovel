package com.example.readnovel.Model;

public class Chapter {
    private String name; //Tên truyện
    private String view;
    private String thumbal;
    private String chapter; //Tên chapter
    private String url; //Link chapter

    public Chapter(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Chapter(String name, String view, String thumbal, String chapter, String url) {
        this.name = name;
        this.view = view;
        this.thumbal = thumbal;
        this.chapter = chapter;
        this.url = url;
    }

    public Chapter() {
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getThumbal() {
        return thumbal;
    }

    public void setThumbal(String thumbal) {
        this.thumbal = thumbal;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
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
}

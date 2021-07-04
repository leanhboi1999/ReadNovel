package com.example.readnovel.Model;

public class Comic {
    private String name;
    private String view;
    private String thumbal;
    private String chapter;
    private String linkComic;


    public Comic(String name, String view, String thumbal, String chapter, String linkComic) {
        this.name = name;
        this.view = view;
        this.thumbal = thumbal;
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

    public String getLinkComic() {
        return linkComic;
    }

    public void setLinkComic(String linkComic) {
        this.linkComic = linkComic;
    }
}

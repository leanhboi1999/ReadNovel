package com.example.readnovel.Model;


import io.realm.RealmObject;

public class BookmarkDatabase extends RealmObject {
    private String name; //Tên truyện
    private String view;
    private String thumbal;
    private String chapter; //Tên chapter
    private String linkChapter; //Link chapter

    public BookmarkDatabase(String name, String view, String thumbal, String chapter, String linkChapter) {
        this.name = name;
        this.view = view;
        this.thumbal = thumbal;
        this.chapter = chapter;
        this.linkChapter = linkChapter;
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

    public String getLinkChapter() {
        return linkChapter;
    }

    public void setLinkChapter(String linkChapter) {
        this.linkChapter = linkChapter;
    }

    public BookmarkDatabase() {
    }
}

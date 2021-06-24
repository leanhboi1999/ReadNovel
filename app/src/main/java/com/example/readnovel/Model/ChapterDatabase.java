package com.example.readnovel.Model;


public class ChapterDatabase {
    private String name;
    private String chapter;

    public ChapterDatabase(String name, String chapter) {
        this.name = name;
        this.chapter = chapter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
}

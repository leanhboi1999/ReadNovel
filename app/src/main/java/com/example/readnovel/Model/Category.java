package com.example.readnovel.Model;

public class Category {
    private String urlBook;
    private String thumbalt;
    private String name;
    private String content;

    public Category(String urlBook, String thumbalt, String name, String content) {
        this.urlBook = urlBook;
        this.thumbalt = thumbalt;
        this.name = name;
        this.content = content;
    }

    public String getUrlBook() {
        return urlBook;
    }

    public void setUrlBook(String urlBook) {
        this.urlBook = urlBook;
    }

    public String getThumbalt() {
        return thumbalt;
    }

    public void setThumbalt(String thumbalt) {
        this.thumbalt = thumbalt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.example.readnovel.Model;

public class Category {
    private String urlBook;
    private String thumb;
    private String name;
    private String content;

    public Category(String urlBook, String thumb, String name, String content) {
        this.urlBook = urlBook;
        this.thumb = thumb;
        this.name = name;
        this.content = content;
    }

    public String getUrlBook() {
        return urlBook;
    }

    public void setUrlBook(String urlBook) {
        this.urlBook = urlBook;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
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

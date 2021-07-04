package com.example.readnovel.Model;

public class ComicFirebase {
    private String id;
    private String mame;

    public ComicFirebase(String id, String mame) {
        this.id = id;
        this.mame = mame;
    }

    public ComicFirebase() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMame() {
        return mame;
    }

    public void setMame(String mame) {
        this.mame = mame;
    }
}

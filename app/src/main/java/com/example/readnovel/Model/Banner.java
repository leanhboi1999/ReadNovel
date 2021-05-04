package com.example.readnovel.Model;

public class Banner {
    private String name;
    private String thumbalt;

    public Banner(String name, String thumbalt) {
        this.name = name;
        this.thumbalt = thumbalt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbalt() {
        return thumbalt;
    }

    public void setThumbalt(String thumbalt) {
        this.thumbalt = thumbalt;
    }
}

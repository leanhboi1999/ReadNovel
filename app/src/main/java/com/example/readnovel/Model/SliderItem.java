package com.example.readnovel.Model;

public class SliderItem {
    private String imageURL;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public SliderItem(String imageURL) {
        this.imageURL = imageURL;
    }
    public SliderItem(String imageURL, String name) {
        this.imageURL = imageURL;
        this.name = name;
    }

}

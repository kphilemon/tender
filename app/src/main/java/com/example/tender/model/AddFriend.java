package com.example.tender.model;

public class AddFriend {

    private String image;
    private String name;
    private String info;
    private boolean existing;

    public AddFriend(String name, boolean existing) {
        this.name = name;
        this.existing = existing;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

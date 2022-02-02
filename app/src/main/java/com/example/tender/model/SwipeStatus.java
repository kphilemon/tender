package com.example.tender.model;

import androidx.annotation.Nullable;

public class SwipeStatus {

    private String image;
    private String name;
    private boolean status;

    public SwipeStatus(@Nullable String image, String name, boolean status) {
        this.image = image;
        this.name = name;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}

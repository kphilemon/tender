package com.example.tender.model;

public class SwipeStatus {
    private String displayName;
    private boolean status;
    private String photoUrl;

    public SwipeStatus(String displayName, boolean status, String photoUrl) {
        this.displayName = displayName;
        this.status = status;
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}

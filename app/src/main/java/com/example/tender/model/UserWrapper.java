package com.example.tender.model;

import java.io.Serializable;

// Utility wrapper class to do all sorts of stuffs
public class UserWrapper implements Serializable {
    private String id;
    private User user;
    private boolean isFriend;

    public UserWrapper(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public UserWrapper(String id, User user, boolean isFriend) {
        this.id = id;
        this.user = user;
        this.isFriend = isFriend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}

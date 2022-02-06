package com.example.tender.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String displayName;
    private String about;
    private String photoUrl;

    // array of user ids
    private List<String> friends;
    private List<String> friendRequests;

    // array of match ids
    private List<String> activeMatches;
    private List<String> completedMatches;

    // notifications related
    private boolean isNotificationEnabled;
    private String fcmToken;

    public User() {

    }

    public User(String username, String displayName, String about, String photoUrl, List<String> friends, List<String> friendRequests, List<String> activeMatches, List<String> completedMatches, boolean isNotificationEnabled, String fcmToken) {
        this.username = username;
        this.displayName = displayName;
        this.about = about;
        this.photoUrl = photoUrl;
        this.friends = friends;
        this.friendRequests = friendRequests;
        this.activeMatches = activeMatches;
        this.completedMatches = completedMatches;
        this.isNotificationEnabled = isNotificationEnabled;
        this.fcmToken = fcmToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<String> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public List<String> getActiveMatches() {
        return activeMatches;
    }

    public void setActiveMatches(List<String> activeMatches) {
        this.activeMatches = activeMatches;
    }

    public List<String> getCompletedMatches() {
        return completedMatches;
    }

    public void setCompletedMatches(List<String> completedMatches) {
        this.completedMatches = completedMatches;
    }

    public boolean isNotificationEnabled() {
        return isNotificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        isNotificationEnabled = notificationEnabled;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}

package com.example.tender.model;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.List;

public class Match {
    private String name;
    private Timestamp timestamp;
    private List<String> userIds;
    private HashMap<String, String> displayNames;

    // map user id to list of food ids
    private HashMap<String, List<String>> swipes;

    public Match() {

    }

    public Match(String name, Timestamp timestamp, List<String> userIds, HashMap<String, String> displayNames, HashMap<String, List<String>> swipes) {
        this.name = name;
        this.timestamp = timestamp;
        this.userIds = userIds;
        this.displayNames = displayNames;
        this.swipes = swipes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public HashMap<String, List<String>> getSwipes() {
        return swipes;
    }

    public void setSwipes(HashMap<String, List<String>> swipes) {
        this.swipes = swipes;
    }

    public HashMap<String, String> getDisplayNames() {
        return displayNames;
    }

    public void setDisplayNames(HashMap<String, String> displayNames) {
        this.displayNames = displayNames;
    }
}

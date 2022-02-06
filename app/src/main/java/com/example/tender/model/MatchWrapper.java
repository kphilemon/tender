package com.example.tender.model;

public class MatchWrapper {
    private String id;
    private Match match;
    private boolean isCompleted;

    public MatchWrapper(String id, Match match) {
        this.id = id;
        this.match = match;
    }

    public MatchWrapper(String id, Match match, boolean isCompleted) {
        this.id = id;
        this.match = match;
        this.isCompleted = isCompleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

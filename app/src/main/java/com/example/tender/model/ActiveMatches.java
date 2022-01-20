package com.example.tender.model;

public class ActiveMatches {

    private String matchImage;
    private String matchTitle;
    private String matchDesc;
    private String matchTime;
    private int matchStatus;

    public ActiveMatches(String matchImage, String matchTitle, String matchDesc, String matchTime, int matchStatus) {
        this.matchImage = matchImage;
        this.matchTitle = matchTitle;
        this.matchDesc = matchDesc;
        this.matchTime = matchTime;
        this.matchStatus = matchStatus;
    }

    public String getMatchImage() {
        return matchImage;
    }

    public void setMatchImage(String matchImage) {
        this.matchImage = matchImage;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getMatchDesc() {
        return matchDesc;
    }

    public void setMatchDesc(String matchDesc) {
        this.matchDesc = matchDesc;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public int getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(int matchStatus) {
        this.matchStatus = matchStatus;
    }
}

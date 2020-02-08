package com.example.userapp;

import java.util.Map;

public class Complaint {

    int level;
    String details, userId;
    Map<String, String> timeStamp;

    public Complaint() {
    }

    public Complaint(int level, String details, String userId, Map<String, String> timeStamp) {
        this.level = level;
        this.details = details;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Map<String, String> timeStamp) {
        this.timeStamp = timeStamp;
    }
}

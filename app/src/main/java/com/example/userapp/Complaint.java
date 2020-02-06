package com.example.userapp;

public class Complaint {

    int level;
    String details;

    public Complaint() {
    }

    public Complaint(int level, String details) {
        this.level = level;
        this.details = details;
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
}

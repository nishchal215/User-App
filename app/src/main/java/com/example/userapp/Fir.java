package com.example.userapp;

public class Fir {

    private String complainantId, state, district, place, type, subject, details;


    public Fir(String state) {
    }

    public Fir(String complainantId, String state, String district, String place, String type, String subject, String details) {
        this.complainantId = complainantId;
        this.state = state;
        this.district = district;
        this.place = place;
        this.type = type;
        this.subject = subject;
        this.details = details;
    }


    public String getComplainantId() {
        return complainantId;
    }

    public void setComplainantId(String complainantId) {
        this.complainantId = complainantId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}

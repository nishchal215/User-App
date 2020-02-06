package com.example.userapp;

import java.util.Map;

public class Noc {

    String surname, name, presentAddress, homeAddress, dateOfBirth, placeOfBirth, nocType, charges, identificationMark, fatherName, motherName
            ,spouseName, rcNumber, icNumber, etNumber;
    Map<String, String> timeStamp;

    public Noc() {

    }

    public Noc(String surname, String name, String presentAddress, String homeAddress, String dateOfBirth, String placeOfBirth,
               String nocType, String charges, String identificationMark, String fatherName, String motherName, String spouseName,
               Map<String, String> timeStamp) {
        this.surname = surname;
        this.name = name;
        this.presentAddress = presentAddress;
        this.homeAddress = homeAddress;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.nocType = nocType;
        this.charges = charges;
        this.identificationMark = identificationMark;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.spouseName = spouseName;
        this.timeStamp =timeStamp;
    }

    public Noc(String surname, String name, String presentAddress, String homeAddress, String dateOfBirth, String placeOfBirth,
               String nocType, String rcNumber, String icNumber, String etNumber, Map<String, String> timeStamp) {
        this.surname = surname;
        this.name = name;
        this.presentAddress = presentAddress;
        this.homeAddress = homeAddress;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.nocType = nocType;
        this.rcNumber = rcNumber;
        this.icNumber = icNumber;
        this.etNumber = etNumber;
        this.timeStamp =timeStamp;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getNocType() {
        return nocType;
    }

    public void setNocType(String nocType) {
        this.nocType = nocType;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getIdentificationMark() {
        return identificationMark;
    }

    public void setIdentificationMark(String identificationMark) {
        this.identificationMark = identificationMark;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getRcNumber() {
        return rcNumber;
    }

    public void setRcNumber(String rcNumber) {
        this.rcNumber = rcNumber;
    }

    public String getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public String getEtNumber() {
        return etNumber;
    }

    public void setEtNumber(String etNumber) {
        this.etNumber = etNumber;
    }

    public Map<String, String> getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Map<String, String> timeStamp) {
        this.timeStamp = timeStamp;
    }
}

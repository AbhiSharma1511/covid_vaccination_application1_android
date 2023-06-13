package com.example.covidvaccinationapplication1.Admin;

public class AdminModel {

    private String location="Location";
    private String pin="PinCode";
    private String date="Date";
    private String time="Time";

    public AdminModel() {
    }

    public AdminModel(String location, String pin, String date, String time) {
        this.location = location;
        this.pin = pin;
        this.date = date;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

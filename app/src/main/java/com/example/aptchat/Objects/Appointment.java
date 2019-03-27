package com.example.aptchat.Objects;

import java.util.Calendar;

public class Appointment {
    String service;
    String techname;
    Calendar time;
    int duration;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTechname() {
        return techname;
    }

    public void setTechname(String techname) {
        this.techname = techname;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPositionID() {
        return positionID;
    }

    public void setPositionID(String positionID) {
        this.positionID = positionID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Appointment(String service, String techname, Calendar time, int duration, String positionID, String status) {
        this.service = service;
        this.techname = techname;
        this.time = time;
        this.duration = duration;
        this.positionID = positionID;
        this.status = status;
    }

    String positionID;
    String status;


}

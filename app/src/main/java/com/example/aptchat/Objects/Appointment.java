package com.example.aptchat.Objects;

public class Appointment {
    String service;
    String techname;
    String time;
    String duration;
    String positionID;
    String status;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
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

    public Appointment(String service, String techname, String time, String duration, String positionID, String status) {
        this.service = service;
        this.techname = techname;
        this.time = time;
        this.duration = duration;
        this.positionID = positionID;
        this.status = status;
    }
}

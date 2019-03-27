package com.example.aptchat.Objects;

public class Services {
    String name;
    int duration;
    String type;

    public Services(String name, int duration, String type) {
        this.name = name;
        this.duration = duration;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.example.alex.museumfunds.model;

public class Exhibition extends BaseColumns {
    private Organiser organiser;
    private String duration;

    public Exhibition() {

    }

    public Organiser getOrganiser() {
        return organiser;
    }

    public void setOrganiser(Organiser organiser) {
        this.organiser = organiser;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

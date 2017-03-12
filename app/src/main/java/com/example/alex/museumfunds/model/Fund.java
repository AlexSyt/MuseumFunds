package com.example.alex.museumfunds.model;

public class Fund extends BaseColumns {
    private String description;

    public Fund() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

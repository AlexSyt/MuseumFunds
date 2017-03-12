package com.example.alex.museumfunds.model;

public class Author extends BaseColumns {
    private String dob;
    private String country;

    public Author() {

    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

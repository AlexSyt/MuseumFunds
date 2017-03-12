package com.example.alex.museumfunds.model;

public class Organiser extends BaseColumns {
    private String address;
    private String phoneNumber;

    public Organiser() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

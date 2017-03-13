package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "organisers")
public class Organiser extends BaseColumns {

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String address;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
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

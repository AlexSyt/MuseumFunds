package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "organisers")
public class Organiser extends BaseEntity {

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String address;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String phoneNumber;

    public Organiser() {

    }

    public Organiser(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

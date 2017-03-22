package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "authors")
public class Author extends BaseEntity {

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String dob;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String country;

    public Author() {

    }

    public Author(String name, String dob, String country) {
        this.name = name;
        this.dob = dob;
        this.country = country;
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

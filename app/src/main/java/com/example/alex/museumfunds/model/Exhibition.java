package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "exhibitions")
public class Exhibition extends BaseEntity {

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Organiser organiser;

    @DatabaseField(canBeNull = false, dataType = DataType.DATE)
    private Date startDate;

    @DatabaseField(canBeNull = false, dataType = DataType.DATE)
    private Date endDate;

    public Exhibition() {

    }

    public Exhibition(String name, Organiser organiser, Date startDate, Date endDate) {
        this.name = name;
        this.organiser = organiser;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Organiser getOrganiser() {
        return organiser;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return name;
    }
}

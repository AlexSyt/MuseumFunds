package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "exhibitions")
public class Exhibition extends BaseColumns {

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Organiser organiser;

    @DatabaseField(canBeNull = false, dataType = DataType.DATE)
    private Date startDate;

    @DatabaseField(canBeNull = false, dataType = DataType.DATE)
    private Date endDate;

    public Exhibition() {

    }

    public Organiser getOrganiser() {
        return organiser;
    }

    public void setOrganiser(Organiser organiser) {
        this.organiser = organiser;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

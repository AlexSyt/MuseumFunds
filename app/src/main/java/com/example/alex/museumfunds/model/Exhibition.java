package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exhibitions")
public class Exhibition extends BaseColumns {

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Organiser organiser;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
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

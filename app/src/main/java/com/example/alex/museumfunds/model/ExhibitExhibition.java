package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "exhibits_exhibitions")
public class ExhibitExhibition {

    @DatabaseField(canBeNull = false, dataType = DataType.UUID, id = true)
    private UUID id = UUID.randomUUID();

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Exhibit exhibit;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Exhibition exhibition;

    public ExhibitExhibition() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Exhibit getExhibit() {
        return exhibit;
    }

    public void setExhibit(Exhibit exhibit) {
        this.exhibit = exhibit;
    }

    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }
}

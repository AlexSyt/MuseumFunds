package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "exhibits_exhibitions")
public class ExhibitExhibition {

    public static final String EXHIBIT_ID = "exhibit_id";
    public static final String EXHIBITION_ID = "exhibition_id";

    @DatabaseField(canBeNull = false, dataType = DataType.UUID, id = true)
    private UUID id = UUID.randomUUID();

    @DatabaseField(canBeNull = false, foreign = true, columnName = EXHIBIT_ID)
    private Exhibit exhibit;

    @DatabaseField(canBeNull = false, foreign = true, columnName = EXHIBITION_ID)
    private Exhibition exhibition;

    public ExhibitExhibition() {

    }

    public ExhibitExhibition(Exhibit exhibit, Exhibition exhibition) {
        this.exhibit = exhibit;
        this.exhibition = exhibition;
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

package com.example.alex.museumfunds.model;

import java.util.UUID;

public class ExhibitExhibition {
    private UUID id = UUID.randomUUID();
    private Exhibit exhibit;
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

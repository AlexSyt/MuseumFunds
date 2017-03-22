package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.UUID;

public class BaseColumns {

    @DatabaseField(canBeNull = false, dataType = DataType.UUID, id = true)
    protected UUID id = UUID.randomUUID();

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    protected String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

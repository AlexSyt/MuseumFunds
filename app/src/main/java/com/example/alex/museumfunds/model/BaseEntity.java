package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.UUID;

public class BaseEntity {

    @DatabaseField(canBeNull = false, dataType = DataType.UUID, id = true)
    private UUID id = UUID.randomUUID();

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    protected String name;

    public String getName() {
        return name;
    }
}

package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "funds")
public class Fund extends BaseEntity {

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String description;

    public Fund() {

    }

    public Fund(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

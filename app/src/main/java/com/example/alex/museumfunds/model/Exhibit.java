package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exhibits")
public class Exhibit extends BaseEntity {

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String creationYear;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Author author;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private FundCatalog fundCatalog;

    public Exhibit() {

    }

    public Exhibit(String name, String creationYear, Author author, FundCatalog fundCatalog) {
        this.name = name;
        this.creationYear = creationYear;
        this.author = author;
        this.fundCatalog = fundCatalog;
    }

    public String getCreationYear() {
        return creationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public FundCatalog getFundCatalog() {
        return fundCatalog;
    }

    @Override
    public String toString() {
        return name;
    }
}

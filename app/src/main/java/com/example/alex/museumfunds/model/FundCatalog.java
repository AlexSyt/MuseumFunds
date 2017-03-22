package com.example.alex.museumfunds.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "fund_catalogs")
public class FundCatalog extends BaseEntity {

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Fund fund;

    public FundCatalog() {

    }

    public FundCatalog(String name, Fund fund) {
        this.name = name;
        this.fund = fund;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }
}

package com.example.alex.museumfunds.model;

public class FundCatalog extends BaseColumns {
    private Fund fund;

    public FundCatalog() {

    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }
}

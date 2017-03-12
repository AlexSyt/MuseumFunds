package com.example.alex.museumfunds.model;

public class Exhibit extends BaseColumns {
    private String creationYear;
    private Author author;
    private FundCatalog fundCatalog;

    public Exhibit() {

    }

    public String getCreationYear() {
        return creationYear;
    }

    public void setCreationYear(String creationYear) {
        this.creationYear = creationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public FundCatalog getFundCatalog() {
        return fundCatalog;
    }

    public void setFundCatalog(FundCatalog fundCatalog) {
        this.fundCatalog = fundCatalog;
    }
}

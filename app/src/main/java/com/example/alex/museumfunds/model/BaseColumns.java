package com.example.alex.museumfunds.model;

import java.util.UUID;

class BaseColumns {
    protected UUID id = UUID.randomUUID();
    protected String name;

    public BaseColumns() {

    }

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

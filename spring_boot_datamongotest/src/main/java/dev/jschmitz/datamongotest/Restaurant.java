package dev.jschmitz.datamongotest;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Restaurant {

    @Id
    private String id;
    private String name;
    private Coordinate location;

    protected Restaurant() {
    }

    public Restaurant(String name, Coordinate location) {
        this.name = name;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinate getLocation() {
        return location;
    }
}

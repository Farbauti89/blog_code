package dev.jschmitz.springbootservicetest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
class Package {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private Double length;

    @NotNull
    private Double width;

    @NotNull
    private Double height;

    @NotNull
    private Double weight;

    protected Package() {
    }

    Package(Double length, Double width, Double height, Double weight) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    String getId() {
        return id;
    }

    Double getLength() {
        return length;
    }

    Double getWidth() {
        return width;
    }

    Double getHeight() {
        return height;
    }

    Double getWeight() {
        return weight;
    }
}

package dev.jschmitz.datajpatest;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private Set<Rating> ratings;

    protected Restaurant(){}

    public Restaurant(String name) {
        this.name = name;
        ratings = new HashSet<>();
    }

    public void rate(Integer score){
        var rating = new Rating(score, this);
        ratings.add(rating);
    }

    public Set<Rating> getRatings() {
        return ratings;
    }
}

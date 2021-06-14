package dev.jschmitz.datajpatest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "SELECT r FROM Restaurant r LEFT JOIN r.ratings ra GROUP BY r having avg(ra.score) >= 8")
    List<Restaurant> findTopRatedRestaurants();

}

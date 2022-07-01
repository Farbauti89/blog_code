package dev.jschmitz.datajpatest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>,
                                              JpaSpecificationExecutor<Restaurant> {

    @Query(value = "SELECT r FROM Restaurant r LEFT JOIN r.ratings ra GROUP BY r having avg(ra.score) >= 8")
    List<Restaurant> findTopRatedRestaurants();

    @Query(value = "SELECT r FROM Restaurant r LEFT JOIN r.ratings ra "
                   + "where r.name like %:name% GROUP BY r having avg(ra.score) >= 8")
    List<Restaurant> findTopRatedRestaurantsByName(@Param("name") String name);
}

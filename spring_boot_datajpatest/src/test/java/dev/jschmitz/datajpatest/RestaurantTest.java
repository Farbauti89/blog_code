package dev.jschmitz.datajpatest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RestaurantTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Sql("/fixture/restaurantTest_topRatedRestaurant.sql")
    void restaurantsWithAnAverageRatingOfEight_AreTopRatedRestaurants() {
        List<Restaurant> topRestaurants = restaurantRepository.findTopRatedRestaurantsByName("Spring");
        assertEquals(1, topRestaurants.size());
    }

    @Test
    void restaurantsWithAnAverageRatingOfEight_AreTopRatedRestaurants2() {

        jdbcTemplate.update("INSERT INTO restaurant (id, name) VALUES (1, 'Café Java');");
        jdbcTemplate.update("INSERT INTO rating (restaurant_id, score) VALUES (1, 10);");

        List<Restaurant> topRestaurants = restaurantRepository.findTopRatedRestaurants();
        assertEquals(1, topRestaurants.size());
    }

    @Test
    void restaurantsWithAnAverageRatingOfEight_AreTopRatedRestaurants3() {

        var restaurant = new Restaurant("Café Java");
        restaurant.rate(10);
        entityManager.persist(restaurant);

        List<Restaurant> topRestaurants = restaurantRepository.findTopRatedRestaurants();
        assertEquals(1, topRestaurants.size());
    }

    @Test
    void restaurantsWithAnAverageRatingOfEight_AreTopRatedRestaurants4() {

        var restaurant = new Restaurant("Café Java");
        restaurant.rate(10);
        restaurantRepository.save(restaurant);

        List<Restaurant> topRestaurants = restaurantRepository.findTopRatedRestaurants();
        assertEquals(1, topRestaurants.size());
    }
}

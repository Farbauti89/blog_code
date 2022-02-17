package dev.jschmitz.springboottestcontainers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("postgres")
class RestaurantRefactoredJdbcUrlTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void restaurantsWithAnAverageRatingOfEight_AreTopRatedRestaurants4() {

        var topRestaurant = new Restaurant("Café Java");
        topRestaurant.rate(10);

        var normalRestaurant = new Restaurant("Jakarta Restaurant");
        normalRestaurant.rate(8);
        normalRestaurant.rate(7);

        restaurantRepository.save(topRestaurant);
        restaurantRepository.save(normalRestaurant);

        List<Restaurant> topRestaurants = restaurantRepository.findTopRatedRestaurants();
        assertEquals(1, topRestaurants.size());
        assertEquals("Café Java", topRestaurants.get(0).getName());
    }
}

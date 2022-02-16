package dev.jschmitz.springboottestcontainers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = RestaurantTest.DataSourceInitializer.class)
class RestaurantTest {

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:12.9-alpine");

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void restaurantsWithAnAverageRatingOfEight_AreTopRatedRestaurants() {

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

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            );
        }
    }
}

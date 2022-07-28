package dev.jschmitz.datajpatest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setup() {
        final var cafeJava = new Restaurant("Café Java");
        cafeJava.rate(10);
        restaurantRepository.save(cafeJava);

        final var cafeSpring = new Restaurant("Café Spring");
        cafeSpring.rate(7);
        restaurantRepository.save(cafeSpring);

        final var restaurantSpring = new Restaurant("Spring Restaurant");
        restaurantSpring.rate(10);
        restaurantRepository.save(restaurantSpring);
    }

    @Test
    void findsTopRatedRestaurantsByName() {
        final var cut = new RestaurantService(restaurantRepository);
        final var restaurants = cut.findTopRatedByName("Spring");

        assertThat(restaurants, hasSize(1));
        assertThat(restaurants, contains(hasProperty("name", is("Spring Restaurant"))));
    }
}

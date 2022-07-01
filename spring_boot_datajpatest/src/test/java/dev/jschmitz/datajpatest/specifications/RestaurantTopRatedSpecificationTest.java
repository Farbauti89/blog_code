package dev.jschmitz.datajpatest.specifications;

import java.util.List;

import dev.jschmitz.datajpatest.Restaurant;
import dev.jschmitz.datajpatest.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static dev.jschmitz.datajpatest.specifications.RestaurantSpecifications.hasTopRating;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.jpa.domain.Specification.where;

@DataJpaTest
class RestaurantTopRatedSpecificationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void matchesRestaurantsWithAnAverageRatingAboveEight() {

        var cafeJava = new Restaurant("Café Java");
        cafeJava.rate(10);

        restaurantRepository.save(cafeJava);

        List<Restaurant> topRestaurants = restaurantRepository.findAll(where(hasTopRating()));
        assertEquals(1, topRestaurants.size());
        assertThat(topRestaurants, contains(
                hasProperty("name", is("Café Java"))
        ));
    }

    @Test
    void matchesRestaurantsWithAnAverageRatingEqualToEight() {

        var cafeJava = new Restaurant("Café Java");
        cafeJava.rate(8);

        restaurantRepository.save(cafeJava);

        List<Restaurant> topRestaurants = restaurantRepository.findAll(where(hasTopRating()));
        assertEquals(1, topRestaurants.size());
        assertThat(topRestaurants, contains(
                hasProperty("name", is("Café Java"))
        ));
    }

    @Test
    void matchesRestaurantsWithMultipleRatingsAndAnAverageRatingEqualToEight() {

        var restaurantSpring = new Restaurant("Spring Restaurant");
        restaurantSpring.rate(10);
        restaurantSpring.rate(6);

        restaurantRepository.save(restaurantSpring);

        List<Restaurant> topRestaurants = restaurantRepository.findAll(where(hasTopRating()));
        assertEquals(1, topRestaurants.size());
        assertThat(topRestaurants, contains(
                hasProperty("name", is("Spring Restaurant"))
        ));
    }

    @Test
    void doesNotMatchRestaurantsWithAnAverageRatingBelowEight() {

        var restaurantJakarta = new Restaurant("Jakarta Restaurant");
        restaurantJakarta.rate(8);
        restaurantJakarta.rate(7);

        restaurantRepository.save(restaurantJakarta);

        List<Restaurant> topRestaurants = restaurantRepository.findAll(where(hasTopRating()));
        assertThat(topRestaurants, is(empty()));
    }

    @Test
    void matchesRestaurantsWithAnAverageRatingOfAtLeastOrEqualEight4() {

        var restaurantSpring = new Restaurant("Spring Restaurant");
        restaurantSpring.rate(10);
        restaurantSpring.rate(6);

        restaurantRepository.save(restaurantSpring);

        List<Restaurant> topRestaurants = restaurantRepository.findAll(where(hasTopRating()));
        assertEquals(1, topRestaurants.size());
        assertThat(topRestaurants, contains(
                hasProperty("name", is("Spring Restaurant"))
        ));
    }
}

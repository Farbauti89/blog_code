package dev.jschmitz.datajpatest.specifications;

import dev.jschmitz.datajpatest.Restaurant;
import dev.jschmitz.datajpatest.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static dev.jschmitz.datajpatest.specifications.RestaurantSpecifications.nameIsLike;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.data.jpa.domain.Specification.where;

@DataJpaTest
class RestaurantNameSpecificationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setup() {
        restaurantRepository.save(new Restaurant("Café Java"));
        restaurantRepository.save(new Restaurant("Spring Restaurant"));
        restaurantRepository.save(new Restaurant("Jakarta Restaurant"));
    }

    @Test
    void matchesName() {
        final var topRestaurants = restaurantRepository.findAll(
                where(nameIsLike("Restaurant"))
        );

        assertThat(topRestaurants, hasSize(2));
        assertThat(topRestaurants, contains(
                hasProperty("name", is("Spring Restaurant")),
                hasProperty("name", is("Jakarta Restaurant"))
        ));
    }

    @Test
    void matchesNameIgnoringCase() {
        final var topRestaurants = restaurantRepository.findAll(
                where(nameIsLike("rEsTaUrAnT"))
        );

        assertThat(topRestaurants, hasSize(2));
        assertThat(topRestaurants, contains(
                hasProperty("name", is("Spring Restaurant")),
                hasProperty("name", is("Jakarta Restaurant"))
        ));
    }
}

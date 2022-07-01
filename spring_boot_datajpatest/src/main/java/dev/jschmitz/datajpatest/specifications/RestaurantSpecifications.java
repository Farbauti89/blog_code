package dev.jschmitz.datajpatest.specifications;

import dev.jschmitz.datajpatest.Restaurant;
import org.springframework.data.jpa.domain.Specification;

public final class RestaurantSpecifications {

    private RestaurantSpecifications() {
    }

    public static Specification<Restaurant> hasTopRating() {
        return new RestaurantTopRatedSpecification();
    }

    public static Specification<Restaurant> nameIsLike(String name) {
        return new RestaurantNameSpecification(name);
    }
}

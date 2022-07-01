package dev.jschmitz.datajpatest;

import java.util.List;

import org.springframework.stereotype.Service;

import static dev.jschmitz.datajpatest.specifications.RestaurantSpecifications.hasTopRating;
import static dev.jschmitz.datajpatest.specifications.RestaurantSpecifications.nameIsLike;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findTopRated() {
        return restaurantRepository.findAll(where(hasTopRating()));
    }

    public List<Restaurant> findTopRatedByName(String name) {
        return restaurantRepository.findAll(where(nameIsLike(name)).and(hasTopRating()));
    }
}

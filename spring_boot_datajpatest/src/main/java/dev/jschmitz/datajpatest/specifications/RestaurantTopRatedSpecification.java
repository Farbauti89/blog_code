package dev.jschmitz.datajpatest.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import dev.jschmitz.datajpatest.Rating_;
import dev.jschmitz.datajpatest.Restaurant;
import dev.jschmitz.datajpatest.Restaurant_;
import org.springframework.data.jpa.domain.Specification;

class RestaurantTopRatedSpecification implements Specification<Restaurant> {

    public static final double MINIMAL_AVERAGE_RATING = 8.0;

    @Override
    public Predicate toPredicate(Root<Restaurant> restaurant,
                                 CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final var averageRating = criteriaBuilder.avg(restaurant.join(Restaurant_.RATINGS).get(Rating_.SCORE));
        final var topRatedPredicate = criteriaBuilder.greaterThanOrEqualTo(averageRating, MINIMAL_AVERAGE_RATING);
        query.groupBy(restaurant.get(Restaurant_.ID)).having(topRatedPredicate);

        return null;
    }
}

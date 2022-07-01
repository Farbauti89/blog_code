package dev.jschmitz.datajpatest.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import dev.jschmitz.datajpatest.Restaurant;
import dev.jschmitz.datajpatest.Restaurant_;
import org.springframework.data.jpa.domain.Specification;

class RestaurantNameSpecification implements Specification<Restaurant> {

    private final String name;

    public RestaurantNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Restaurant> restaurant, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final var nameExpression = criteriaBuilder.lower(restaurant.get(Restaurant_.name));
        return criteriaBuilder.like(nameExpression, "%" + name.toLowerCase() + "%");
    }
}

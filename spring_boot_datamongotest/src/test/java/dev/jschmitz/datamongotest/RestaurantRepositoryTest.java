package dev.jschmitz.datamongotest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
class RestaurantRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @AfterEach
    void cleanUpDatabase() {
        mongoTemplate.getDb().drop();
    }

    @Test
    void bootstrapTestDataWithMongoRepository() {
        var circle = new Circle(new Point(52.52437, 13.41053), new Distance(2, Metrics.KILOMETERS));

        var restaurant = new Restaurant("Café Java", new Coordinate(52.52439, 13.41043));
        var restaurant2 = new Restaurant("Spring Restaurant", new Coordinate(52.52447, 13.41024));
        var restaurant3 = new Restaurant("Jakarta Restaurant", new Coordinate(52.52447, 13.41022));

        restaurantRepository.save(restaurant);
        restaurantRepository.save(restaurant2);
        restaurantRepository.save(restaurant3);

        assertEquals(2, restaurantRepository.findByLocationWithin(circle).size());
    }

    @Test
    void findByLocationWithin_FindsRestaurantsWithinAGivenDistance() {
        var circle = new Circle(new Point(52.52437, 13.41053), new Distance(2, Metrics.KILOMETERS));

        var restaurant = new Restaurant("Café Java", new Coordinate(52.52439, 13.41043));
        var restaurant2 = new Restaurant("Spring Restaurant", new Coordinate(52.52447, 13.41024));
        var restaurant3 = new Restaurant("Jakarta Restaurant", new Coordinate(52.52447, 13.41022));

        mongoTemplate.insert(restaurant);
        mongoTemplate.insert(restaurant2);
        mongoTemplate.insert(restaurant3);

        assertEquals(2, restaurantRepository.findByLocationWithin(circle).size());
    }

}
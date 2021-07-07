package dev.jschmitz.datamongotest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@Import(RestaurantPopulatorConfiguration.class)
class RestaurantRepositoryWithDataPopulatorTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void bootstrapTestDataWithMongoRepository() {
        assertEquals(1, restaurantRepository.count());
    }

}
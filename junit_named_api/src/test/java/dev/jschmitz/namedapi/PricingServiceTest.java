package dev.jschmitz.namedapi;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceTest {

    static Stream<Arguments> comics() {
        return Stream.of(Arguments.of(new Comic("JVMan and Javagirl", Comic.Condition.MINT, Comic.PrintStatus.ORIGINAL)),
                         Arguments.of(new Comic("JVMan and Javagirl", Comic.Condition.GOOD, Comic.PrintStatus.ORIGINAL)),
                         Arguments.of(new Comic("JVMan and Javagirl", Comic.Condition.MEDIOCRE, Comic.PrintStatus.ORIGINAL)),
                         Arguments.of(new Comic("JVMan and Javagirl", Comic.Condition.BAD, Comic.PrintStatus.ORIGINAL)),
                         Arguments.of(new Comic("JVMan and Javagirl", Comic.Condition.MINT, Comic.PrintStatus.REPRINT)),
                         Arguments.of(new Comic("JVMan and Javagirl", Comic.Condition.GOOD, Comic.PrintStatus.REPRINT)),
                         Arguments.of(new Comic("JVMan and Javagirl", Comic.Condition.MEDIOCRE, Comic.PrintStatus.REPRINT)),
                         Arguments.of(new Comic("JVMan and Javagirl", Comic.Condition.BAD, Comic.PrintStatus.REPRINT))
        );
    }

    @ParameterizedTest
    @MethodSource("comics")
    void calculatePurchasePrice(Comic comic) {

        var cut = new PricingService();
        var price = cut.calculatePurchasePrice(comic);

        assertEquals(42.0, price);
    }
}

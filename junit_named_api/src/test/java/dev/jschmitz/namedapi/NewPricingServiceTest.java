package dev.jschmitz.namedapi;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewPricingServiceTest {

    static Stream<Arguments> comics() {
        return Stream.of(Arguments.of(Named.of("a mint original", new Comic("JVMan and Javagirl", Comic.Condition.MINT, Comic.PrintStatus.ORIGINAL))),
                         Arguments.of(Named.of("a good original", new Comic("JVMan and Javagirl", Comic.Condition.GOOD, Comic.PrintStatus.ORIGINAL))),
                         Arguments.of(Named.of("a mediocre original", new Comic("JVMan and Javagirl", Comic.Condition.MEDIOCRE, Comic.PrintStatus.ORIGINAL))),
                         Arguments.of(Named.of("a bad original", new Comic("JVMan and Javagirl", Comic.Condition.BAD, Comic.PrintStatus.ORIGINAL))),
                         Arguments.of(Named.of("a mint reprint", new Comic("JVMan and Javagirl", Comic.Condition.MINT, Comic.PrintStatus.REPRINT))),
                         Arguments.of(Named.of("a good reprint", new Comic("JVMan and Javagirl", Comic.Condition.GOOD, Comic.PrintStatus.REPRINT))),
                         Arguments.of(Named.of("a mediocre reprint", new Comic("JVMan and Javagirl", Comic.Condition.MEDIOCRE, Comic.PrintStatus.REPRINT))),
                         Arguments.of(Named.of("a bad reprint", new Comic("JVMan and Javagirl", Comic.Condition.BAD, Comic.PrintStatus.REPRINT)))
        );
    }

    @DisplayName("Calculates the price for:")
    @ParameterizedTest
    @MethodSource("comics")
    void calculatePurchasePrice(Comic comic) {

        var cut = new PricingService();
        var price = cut.calculatePurchasePrice(comic);

        assertEquals(42.0, price);
    }
}

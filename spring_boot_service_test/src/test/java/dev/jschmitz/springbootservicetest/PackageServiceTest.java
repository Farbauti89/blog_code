package dev.jschmitz.springbootservicetest;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PackageServiceTest {

    static private Stream<Arguments> getInvalidPackageDimensions() {
        return Stream.of(
                Arguments.of(new Package(0.5, 11.0, 15.0, 10.0)),
                Arguments.of(new Package(11.0, 0.5, 15.0, 10.0)),
                Arguments.of(new Package(1.0, 11.0, 0.5, 10.0))
        );
    }

    @Test
    void weightIsExceededTest() {
        Package invalidPackage = new Package(30.0, 30.0, 30.0, 32.0);
        var packageRepository = mock(PackageRepository.class);

        var cut = new PackageService(packageRepository);

        assertThrows(MaxPackageWeightExceededException.class,
                     () -> cut.acceptPackage(invalidPackage));
    }

    @ParameterizedTest
    @MethodSource("getInvalidPackageDimensions")
    void dimensionsAreToSmallTest(Package p) {

        var packageRepository = mock(PackageRepository.class);

        var cut = new PackageService(packageRepository);

        assertThrows(InvalidPackageDimensionsException.class,
                     () -> cut.acceptPackage(p));
    }

    @Test
    void dimensionsAreToBig() {

        Package invalidPackage = new Package(60.0, 60.0, 181.0, 20.0);
        var packageRepository = mock(PackageRepository.class);

        var cut = new PackageService(packageRepository);

        assertThrows(InvalidPackageDimensionsException.class,
                     () -> cut.acceptPackage(invalidPackage));
    }

    @Test
    void packageWasPersisted() throws MaxPackageWeightExceededException, InvalidPackageDimensionsException {

        var p = new Package(30.0, 30.0, 30.0, 30.0);
        var packageRepository = mock(PackageRepository.class);

        when(packageRepository.save(any(Package.class))).then(returnsFirstArg());

        var cut = new PackageService(packageRepository);
        var persistedPackage = cut.acceptPackage(p);

        verify(packageRepository, times(1)).save(any(Package.class));
        assertEquals(30, persistedPackage.getWeight());
        assertEquals(30, persistedPackage.getHeight());
        assertEquals(30, persistedPackage.getWidth());
        assertEquals(30, persistedPackage.getLength());
    }
}
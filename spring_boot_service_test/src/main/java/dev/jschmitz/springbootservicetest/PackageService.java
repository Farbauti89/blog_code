package dev.jschmitz.springbootservicetest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
class PackageService {

    static final double MAX_WEIGHT = 31.5;
    static final double MAX_SUM_OF_SIDE_LENGTHS = 300.0;

    private final PackageRepository packageRepository;

    PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    Package acceptPackage(Package p) throws MaxPackageWeightExceededException, InvalidPackageDimensionsException {

        if (p.getWeight() > MAX_WEIGHT) {
            throw new MaxPackageWeightExceededException(p.getWeight());
        }

        List<Double> dimensions = Stream.of(p.getWidth(), p.getLength(), p.getHeight()).sorted(Double::compareTo).collect(Collectors.toList());
        if (dimensions.get(0) < 1.0 || dimensions.get(1) < 11.0 || dimensions.get(2) < 15.0) {
            throw InvalidPackageDimensionsException.toSmall(dimensions.get(0), dimensions.get(1), dimensions.get(2));
        }

        if (p.getLength() + p.getWidth() + p.getHeight() > MAX_SUM_OF_SIDE_LENGTHS) {
            throw InvalidPackageDimensionsException.toBig(dimensions.get(0), dimensions.get(1), dimensions.get(2));
        }

        return packageRepository.save(p);
    }
}

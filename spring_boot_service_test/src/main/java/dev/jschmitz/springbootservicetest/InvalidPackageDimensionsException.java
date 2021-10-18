package dev.jschmitz.springbootservicetest;

class InvalidPackageDimensionsException extends Exception {

    private InvalidPackageDimensionsException(String message) {
        super(message);
    }

    static InvalidPackageDimensionsException toSmall(Double length, Double width, Double height) {
        return new InvalidPackageDimensionsException(String.format("The package dimension of %s x %s x %s (LxBxH) is to small", length, width, height));
    }

    static InvalidPackageDimensionsException toBig(Double length, Double width, Double height) {
        return new InvalidPackageDimensionsException(String.format("The package dimension of %s x %s x %s (LxBxH) is to big", length, width, height));
    }
}

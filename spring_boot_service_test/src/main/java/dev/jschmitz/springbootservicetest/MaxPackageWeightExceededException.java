package dev.jschmitz.springbootservicetest;

class MaxPackageWeightExceededException extends Exception {

    MaxPackageWeightExceededException(Double weight) {
        super(String.format("The weight of %s kg exceeds the maximum package weight.", weight));
    }
}

package dev.jschmitz.jsontest;

public class Ean {

    private String value;

    private Ean(String value) {
        this.value = value;
    }

    public static Ean of(String value){
        return new Ean(value);
    }

    public String getValue() {
        return value;
    }
}

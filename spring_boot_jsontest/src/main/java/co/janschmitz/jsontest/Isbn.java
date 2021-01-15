package co.janschmitz.jsontest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Isbn {

    @JsonProperty("isbn")
    private String value;

    private Isbn() {
    }

    private Isbn(String value) {
        this.value = value;
    }

    public static Isbn of(String value){
        return new Isbn(value);
    }

    public String getValue() {
        return value;
    }
}

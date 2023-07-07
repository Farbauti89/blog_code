package dev.jschmitz.webclienttest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Character(
        String name,
        @JsonProperty("birth_year")
        String birthYear,
        @JsonProperty("eye_color")
        String eyeColor,
        String gender,
        double height,
        double mass
) {

}

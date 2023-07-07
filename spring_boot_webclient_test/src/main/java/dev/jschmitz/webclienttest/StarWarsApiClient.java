package dev.jschmitz.webclienttest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface StarWarsApiClient {

    @GetExchange("/people/{id}")
    Character findById(@PathVariable Long id);
}

package dev.jschmitz.webclienttest;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class StarWarsCharacterRepository {

    private final StarWarsApiClient client;

    public StarWarsCharacterRepository(StarWarsApiClient client) {
        this.client = client;
    }

    public Optional<Character> findById(Long id) {
        try {
            return Optional.of(client.findById(id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

package dev.jschmitz.spring_boot_restclienttest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Repository
public class StarWarsCharacterRepository {

    private final RestTemplate restTemplate;

    public StarWarsCharacterRepository(RestTemplateBuilder restTemplateBuilder, @Value("${swapi.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
    }

    public Optional<Character> findById(Long id) {

        ResponseEntity<Character> response;
        try {
            response = restTemplate.getForEntity("/people/{id}", Character.class, id);
        } catch (HttpStatusCodeException e) {
            return Optional.empty();
        }

        return Optional.ofNullable(response.getBody());
    }
}

package dev.jschmitz.spring_boot_restclienttest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest({StarWarsCharacterRepository.class})
class StarWarsCharacterRepositoryTest {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private StarWarsCharacterRepository starWarsCharacterRepository;

    @AfterEach
    void resetMockServer() {
        mockRestServiceServer.reset();
    }

    @Test
    void findById_ReturnsTheCharacter() {

        final var idLuke = 1L;
        final var lukeJson = new ClassPathResource("luke.json");

        mockRestServiceServer
                .expect(requestTo("/people/" + idLuke))
                .andRespond(withSuccess(lukeJson, MediaType.APPLICATION_JSON));

        var character = starWarsCharacterRepository.findById(idLuke);

        assertTrue(character.isPresent());
        assertEquals("Luke Skywalker", character.get().name());
        assertEquals("19BBY", character.get().birthYear());
        assertEquals("blue", character.get().eyeColor());
        assertEquals("male", character.get().gender());
        assertEquals(77, character.get().mass());
        assertEquals(172, character.get().height());
    }

    @Test
    void findById_WithIdOfUnknownCharacter_ReturnsEmptyOptional() {

        final var idUnknownCharacter = 999999L;

        mockRestServiceServer
                .expect(requestTo("/people/" + idUnknownCharacter))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        var character = starWarsCharacterRepository.findById(idUnknownCharacter);

        assertTrue(character.isEmpty());
    }
}
package dev.jschmitz.spring_boot_restclienttest;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@MockServerTest({"swapi.baseUrl=http://localhost:${mockServerPort}"})
class CharacterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private MockServerClient mockServerClient;

    @Test
    void requestingAKnownCharacter_Returns200() throws Exception {

        final var resource = new ClassPathResource("luke.json");
        final var mockedResponse = Files.readString(Path.of(resource.getURI()));

        mockServerClient
                .when(request().withPath("/people/1"))
                .respond(response()
                                 .withStatusCode(HttpStatusCode.OK_200.code())
                                 .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                                 .withBody(mockedResponse));

        mockMvc.perform(get("/characters/1"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.name", equalTo("Luke Skywalker")));
    }

    @Test
    void requestingAnUnknownCharacter_Returns404() throws Exception {
        mockServerClient
                .when(request().withPath("/people/1337"))
                .respond(response().withStatusCode(HttpStatusCode.NOT_FOUND_404.code()));

        mockMvc.perform(get("/characters/1").contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isNotFound());
    }
}

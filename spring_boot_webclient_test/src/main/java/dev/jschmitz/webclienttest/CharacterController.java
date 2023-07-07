package dev.jschmitz.webclienttest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final StarWarsCharacterRepository repository;

    public CharacterController(StarWarsCharacterRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Character findById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new CharacterNotFoundException(id));
    }
}

package dev.jschmitz.springsecurityexampleauthentication;

import java.security.Principal;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String helloWorld(Principal principal) {
        return Optional.ofNullable(principal)
                       .map(p -> "Hello, %s".formatted(p.getName()))
                       .orElse("Hello, world");
    }
}

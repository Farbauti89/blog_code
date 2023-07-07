package dev.jschmitz.webclienttest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class StarWarsApiClientConfiguration {

    @Bean
    public StarWarsApiClient starWarsApiClient() {
        WebClient client = WebClient.builder().baseUrl("https://swapi.dev/api").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();

        return factory.createClient(StarWarsApiClient.class);
    }
}

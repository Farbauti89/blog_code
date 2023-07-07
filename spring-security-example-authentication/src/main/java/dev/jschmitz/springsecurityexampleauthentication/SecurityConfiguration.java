package dev.jschmitz.springsecurityexampleauthentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .addFilterBefore(new TrustMeAuthorizationFilter(), BasicAuthenticationFilter.class)
                //.oauth2ResourceServer(configurer -> configurer.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(
                        authorize -> authorize.anyRequest().authenticated()
                )
                .build();
    }
}

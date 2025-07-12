package com.horovyak.adduserdemo.config;

import com.horovyak.adduserdemo.properties.KeycloakProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak(KeycloakProperties keycloakProperties) {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getServerUrl())
                .realm(keycloakProperties.getRealm())
                .grantType("client_credentials")
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .build();
    }

}
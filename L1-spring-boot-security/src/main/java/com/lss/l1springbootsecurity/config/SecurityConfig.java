package com.lss.l1springbootsecurity.config;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ✅ ОБОВ'ЯЗКОВИЙ БІН ДЛЯ НАЛАШТУВАННЯ ПРАВИЛ БЕЗПЕКИ
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Дозволяємо доступ до публічного ендпоінту для всіх
                        .requestMatchers("/public").permitAll()
                        // ЗАХИСТ ОДНОГО ЕНДПОІНТУ: вимагаємо роль "user"
                        .requestMatchers("/secured").hasRole("user")
                        // Будь-які інші запити вимагають аутентифікації
                        .anyRequest().authenticated()
                )
                // Вмикаємо підтримку OAuth2 Resource Server з валідацією JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                // Додаємо наш кастомний конвертер для ролей
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    // ОБОВ'ЯЗКОВИЙ КОНВЕРТЕР ДЛЯ ПРАВИЛЬНОГО МАРІНГУ РОЛЕЙ З KEYCLOAK
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess == null || realmAccess.isEmpty()) {
                return java.util.Collections.emptyList();
            }
            Collection<String> roles = realmAccess.get("roles");
            if (roles == null || roles.isEmpty()) {
                return java.util.Collections.emptyList();
            }
            // Додаємо префікс "ROLE_" до кожної ролі, як цього очікує Spring Security
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        });
        return jwtConverter;
    }
}

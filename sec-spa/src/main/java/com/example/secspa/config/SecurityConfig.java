package com.example.secspa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ВИМКНЕМО CSRF, оскільки ми використовуємо OAuth2
                // stateless API з токенами
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/public").permitAll()
                        // ЗАХИСТ ЕНДПОІНТУ: Вимагаємо нашу нову роль "app_user"
                        .requestMatchers("/private").hasRole("app_user")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );
        return http.build();
    }

    // ОБОВ'ЯЗКОВИЙ КОНВЕРТЕР для ролей з Keycloak
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Claim - твердження
            // це будь-яка частина інформації про користувача або про сам токен,
            // яка передається всередині JWT у вигляді пари "ключ-значення"
            // Прізвище: Коваленко ("family_name": "Kovalenko")
            // Ім'я: Анна ("given_name": "Anna")
            // Ким виданий: МВС України ("iss": "mvs_ukraine")
            // Дійсний до: 31.12.2030 ("exp": 1924982399)
            //Claims роблять JWT-токен самодостатнім.
            //Коли сервер отримує токен, йому не потрібно робити додатковий запит до бази даних, щоб дізнатися, хто цей користувач і які у нього права.
            // Вся необхідна інформація вже є всередині токену
            //Провести авторизацію:
            // Перевірити, чи є у користувача потрібна роль (наприклад, ADMIN) для доступу до ресурсу.
            //Здійснити персоналізацію: Показати на сторінці ім'я користувача (name) або його email.
            //Забезпечити безпеку: Перевірити, чи не закінчився термін дії токену (exp) і чи виданий він довіреним сервером (iss).
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess == null || realmAccess.isEmpty()) return List.of();
            return realmAccess.get("roles").stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        });
        return jwtConverter;
    }

    // ✅ ОБОВ'ЯЗКОВА КОНФІГУРАЦІЯ CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:63342"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

package com.example.zad_2.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home").authenticated() // Сторінка /home вимагає аутентифікації
                        .anyRequest().permitAll() // Всі інші (включаючи / і /login) доступні для всіх
                )
                .formLogin(form -> form
                        .loginPage("/login") // ✅ Вказуємо URL нашої сторінки логіну
                        .defaultSuccessUrl("/home", true) // ✅ Вказуємо, куди редіректити після успішного входу
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") // Куди редіректити після виходу
                );
        return http.build();
    }
}
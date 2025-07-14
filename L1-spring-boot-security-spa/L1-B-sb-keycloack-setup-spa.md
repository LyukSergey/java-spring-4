# Налаштування Spring Boot з Keycloak для SPA (Single Page Application)
### \#\# Крок 1: Налаштування нового Realm у Keycloak (ОБОВ'ЯЗКОВО)

1.  **Запустіть Keycloak** (якщо ще не запущено) на `http://localhost:8180`.

2.  **Створіть новий Realm:**

    * Увійдіть в **Administration Console**.
    * Наведіть на поточний realm (напр. "Master") і натисніть **Create Realm**.
    * **Realm (королівство) name**: `my-prod-app-realm`
    * Натисніть **Create**.

3.  **Створіть нового Клієнта для Front-End:**

    * У меню зліва перейдіть у **Clients** -\> **Create client**.
    * **Client ID**: `my-webapp-client`
    * Натисніть **Next**.
    * **ОБОВ'ЯЗКОВІ НАЛАШТУВАННЯ:**
        * **Client authentication**: `OFF` (це публічний клієнт).
        * **Authorization**: Увімкніть `Standard flow`.
        * **Valid Redirect URIs**: `http://localhost:63342/*` (адреса, куди можна повернутися після логіну).
        * **Web Origins**: `http://localhost:63342` (адреса, з якої дозволені запити до Keycloak).
    * Натисніть **Save**.

4.  **Створіть нову Роль та Користувача:**

    * **Роль:** Перейдіть у **Realm Roles** -\> **Create role**. **Role name**: `app_user`.
    * **Користувач:** Перейдіть у **Users** -\> **Create user**.
        * **Username**: `produser`.
        * Перейдіть на вкладку **Credentials** та встановіть пароль (напр. `password`).
        * Перейдіть на вкладку **Role mapping**, натисніть **Assign role** та додайте роль `app_user`.

-----

### \#\# Крок 2: Створення та Конфігурація нового Spring Boot проєкту

1.  **Створіть проєкт на `start.spring.io`:**

    * **Artifact**: `prod-ready-api`
    * **ОБОВ'ЯЗКОВІ ЗАЛЕЖНОСТІ:** `Spring Web`, `Spring Security`, `OAuth2 Resource Server`.

2.  **Налаштуйте `application.yml` (ОБОВ'ЯЗКОВО):**
    Ми будемо використовувати формат `.yml` для кращої читабельності. Створіть/перейменуйте `application.yml` у `src/main/resources/`.

    ```yaml
    spring:
      security:
        oauth2:
          resourceserver:
            jwt:
              # ✅ ОБОВ'ЯЗКОВО: Вказуємо на наш новий realm
              issuer-uri: http://localhost:8081/realms/my-prod-app-realm
    ```

    **Акцент:** Це єдина властивість, потрібна для бекенда, щоб валідувати токени. Він не знає ніяких секретів клієнта.

3.  **Налаштуйте `SecurityConfig.java` (ОБОВ'ЯЗКОВА РЕАЛІЗАЦІЯ):**
    Створіть клас конфігурації безпеки.

    ```java
    package com.example.prodreadyapi.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/public").permitAll()
                    // ✅ ЗАХИСТ ЕНДПОІНТУ: Вимагаємо нашу нову роль "app_user"
                    .requestMatchers("/private").hasRole("app_user")
                    .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );
            return http.build();
        }

        // ✅ ОБОВ'ЯЗКОВИЙ КОНВЕРТЕР для ролей з Keycloak
        private JwtAuthenticationConverter jwtAuthenticationConverter() {
            JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
            jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
                // Claim - твердження
                // це будь-яка частина інформації про користувача або про сам токен, яка передається всередині JWT у вигляді пари "ключ-значення"
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
            configuration.setAllowedOrigins(List.of("http://localhost:3000"));
            configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }
    ```

4.  **Створіть REST-контролер:**

    ```java
    package com.example.prodreadyapi.controller;

    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.oauth2.jwt.Jwt;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping
    public class MainController {

        @GetMapping("/public")
        public String getPublicData() {
            return "Це публічні дані.";
        }

        @GetMapping("/private")
        public String getPrivateData(@AuthenticationPrincipal Jwt jwt) {
            return "Це приватні дані для користувача: " + jwt.getSubject();
        }
    }
    ```

-----

### \#\# Крок 3: Створення Front-End сторінки (`index.html`)

Збережіть цей код як `index.html` і відкрийте його у браузері.

```html
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Prod App Demo</title>
    <script src="http://localhost:8081/js/keycloak.js"></script>
    <style> body { font-family: sans-serif; padding: 2em; } button { padding: 10px; margin: 5px; } pre { background-color: #eee; padding: 1em; } </style>
</head>
<body>
    <h1>Prod-Ready Демо</h1>
    <div>Статус: <span id="status">Ініціалізація...</span></div>
    <hr>
    <button id="loginBtn" onclick="keycloak.login()">Увійти</button>
    <button id="logoutBtn" onclick="keycloak.logout()">Вийти</button>
    <hr>
    <button id="apiBtn" onclick="callApi()">Викликати приватний ендпоінт (/private)</button>
    <div id="api-response" style="margin-top: 1em;"></div>
    <h3>Токен:</h3>
    <pre id="token-info">Немає токену.</pre>

<script>
    // 2. Налаштування клієнта
    const keycloak = new Keycloak({
        url: 'http://localhost:8180',
        realm: 'my-prod-app-realm',
        clientId: 'my-webapp-client'
    });

    // 3. Ініціалізація
    keycloak.init({ onLoad: 'check-sso' }).then(authenticated => {
        updateUI(authenticated);
    });

    function updateUI(authenticated) {
        document.getElementById('logoutBtn').style.display = authenticated ? 'inline-block' : 'none';
        document.getElementById('apiBtn').style.display = authenticated ? 'inline-block' : 'none';
        document.getElementById('loginBtn').style.display = authenticated ? 'none' : 'inline-block';
        document.getElementById('status').textContent = authenticated ? 'Аутентифікований' : 'Не аутентифікований';
        document.getElementById('token-info').textContent = authenticated ? JSON.stringify(keycloak.tokenParsed, null, 2) : 'Немає токену.';
    }

    // 4. Виклик API
    function callApi() {
        const apiResponseDiv = document.getElementById('api-response');
        apiResponseDiv.textContent = 'Виконується запит...';
        fetch('http://localhost:8080/private', {
            headers: { 'Authorization': 'Bearer ' + keycloak.token }
        })
        .then(response => response.text())
        .then(data => { apiResponseDiv.textContent = 'Відповідь від сервера: ' + data; })
        .catch(error => { apiResponseDiv.textContent = 'Помилка: ' + error; });
    }
</script>
</body>
</html>
```

### \#\# Як це все запустити і перевірити

1.  **Запустіть Keycloak.**
2.  **Запустіть ваш новий Spring Boot додаток** (`prod-ready-api`).
3.  **Відкрийте файл `index.html`** у вашому браузері.
4.  Натисніть **"Увійти"**. Вас перенаправить на сторінку Keycloak.
5.  Введіть логін `produser` та пароль `password`.
6.  Вас поверне на сторінку `index.html`, але вже в залогіненому стані. Ви побачите вміст вашого токену.
7.  Натисніть **"Викликати приватний ендпоінт"**. Ви повинні побачити повідомлення від бекенду.
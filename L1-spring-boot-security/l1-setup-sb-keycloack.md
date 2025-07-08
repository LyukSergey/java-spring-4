# Налаштування Spring Boot з Keycloak для захисту ендпоінтів
1.  **Налаштування Keycloak**: Запустимо Keycloak через Docker, створимо `realm`, `клієнта` та `користувача`.
2.  **Створення Spring Boot проєкту**: Згенеруємо проєкт з необхідними залежностями.
3.  **Конфігурація Spring Boot**: Налаштуємо зв'язок з Keycloak.
4.  **Налаштування Spring Security**: Створимо клас конфігурації для захисту ендпоінтів.
5.  **Створення REST-контролера**: Реалізуємо один публічний і один захищений ендпоінт.
6.  **Тестування**: Отримаємо токен з Keycloak і перевіримо доступ.

-----

### \#\# Крок 1: Налаштування Keycloak

Найпростіший спосіб запустити Keycloak — через Docker.

1.  **Запустіть Keycloak контейнер:**
    Відкрийте термінал і виконайте команду:

    ```bash
    docker-compose up -d
    ```

    Це запустить Keycloak на `http://localhost:8180` 
    Логін та паролем адміністратора вказані в docker-compose.yml.

2.  **Створіть Realm:**

    * Перейдіть на `http://localhost:8180` і увійдіть в **Administration Console**.
    * Наведіть на "Master" у верхньому лівому кутку і натисніть **Create Realm**.
    * Введіть **Realm name**: `spring-demo-realm` і натисніть **Create**.

3.  **Створіть Клієнта (ОБОВ'ЯЗКОВО):**
    Клієнт — це ваш Spring Boot додаток, який буде використовувати Keycloak для аутентифікації.

    * У меню зліва перейдіть у **Clients** і натисніть **Create client**.
    * **Client ID**: `spring-boot-client`. Натисніть **Next**.
    * **ОБОВ'ЯЗКОВІ НАЛАШТУВАННЯ:**
        * Увімкніть **Client authentication**: `ON`.
        * Увімкніть **Standard flow** та **Direct access grants**.
    * Натисніть **Next -> Save**.
    * Перейдіть на вкладку **Credentials**. Ви побачите **Client secret**. Скопіюйте його, він знадобиться пізніше.

4.  **Створіть Роль та Користувача:**

    * **Роль:** Перейдіть у **Realm Roles** -\> **Create role**. **Role name**: `user`. Натисніть **Save**.
    * **Користувач:** Перейдіть у **Users** -\> **Create user**.
        * **Username**: `testuser`. Натисніть **Create**.
        * Перейдіть на вкладку **Credentials**. Встановіть пароль (наприклад, `password`) і вимкніть `Temporary`.
        * Перейдіть на вкладку **Role mapping**. Натисніть **Assign role**. Виберіть роль `user` і натисніть **Assign**.

**На цьому етапі Keycloak готовий.**

-----

### \#\# Крок 2: Створення та генерація Spring Boot проєкту

1.  Перейдіть на `start.spring.io`.
2.  Налаштуйте проєкт:
    * **Project**: Maven, **Language**: Java
    * **Spring Boot**: Остання стабільна версія
    * **Artifact**: `keycloak-demo`
3.  **ОБОВ'ЯЗКОВІ ЗАЛЕЖНОСТІ:**
    * `Spring Web`: для створення REST-контролерів.
    * `Spring Security`: основний фреймворк безпеки.
    * <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
      </dependency>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
      </dependency>
    * `OAuth2 Resource Server`: для інтеграції з OAuth2/OIDC провайдером, яким є Keycloak.
4.  Натисніть **Generate** та відкрийте проєкт у вашій IDE.

-----

### \#\# Крок 3: Конфігурація Spring Boot

Відкрийте файл `src/main/resources/application.properties` і додайте наступні налаштування.

#### **ОБОВ'ЯЗКОВІ КОНФІГУРАЦІЇ:**

```properties
# Адреса вашого Keycloak сервера та назва Realm
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8180/realms/spring-demo-realm

# Цей параметр потрібен для отримання токену, для валідації він не використовується
# Вставте ваш Client ID та Client Secret з Keycloak
spring.security.oauth2.client.registration.keycloak.client-id=spring-boot-client
spring.security.oauth2.client.registration.keycloak.client-secret=ВАШ_СКОПІЙОВАНИЙ_СЕКРЕТ
spring.security.oauth2.client.registration.keycloak.authorization-grant-type=password
spring.security.oauth2.client.provider.keycloak.token-uri=${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/token
```

* **Акцент:** Властивість `issuer-uri` є найважливішою. 
* Spring Security використовує її для автоматичного завантаження конфігурації 
* OIDC (OpenID Connect) та публічних ключів для валідації JWT токенів.
* Аналогія: Вхід у нічний клуб
  OAuth 2.0 (Авторизація): 
  Ви показуєте на вході VIP-перепустку (Access Token). 
  Охоронець бачить, що у вас є дозвіл увійти в VIP-зону. 
  Він не знає, хто ви, але знає, що вам дозволено тут бути.

  OIDC (Аутентифікація): 
  Ви показуєте на вході паспорт (ID Token). 
  Охоронець бачить ваше ім'я, прізвище та фото. 
  Він може перевірити, що це справді ви.  
  Він підтверджує вашу особу.

-----

### \#\# Крок 4: Клас конфігурації безпеки (ОБОВ'ЯЗКОВА РЕАЛІЗАЦІЯ)

Створіть пакет `config`, а в ньому клас `SecurityConfig.java`.

```java
package com.example.keycloakdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ✅ ОБОВ'ЯЗКОВИЙ БІН ДЛЯ НАЛАШТУВАННЯ ПРАВИЛ БЕЗПЕКИ
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Дозволяємо доступ до публічного ендпоінту для всіх
                .requestMatchers("/api/public").permitAll()
                // ✅ ЗАХИСТ ОДНОГО ЕНДПОІНТУ: вимагаємо роль "user"
                .requestMatchers("/api/secured").hasRole("user")
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

    // ✅ ОБОВ'ЯЗКОВИЙ КОНВЕРТЕР ДЛЯ ПРАВИЛЬНОГО МАРІНГУ РОЛЕЙ З KEYCLOAK
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            //Claim (з англ. "твердження") — це будь-яка частина інформації про користувача або про сам токен, 
            // яка передається всередині JWT. 
            // Це, по суті, пара "ключ-значення".
            //realm_access — це приватний claim, специфічний для Keycloak. 
            // Його головне призначення — передавати інформацію про ролі користувача, 
            // які визначені на рівні всього realm'у.
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
```

* **Акцент:** Конвертер `jwtAuthenticationConverter` є **обов'язковим**, оскільки Keycloak зберігає ролі у вкладеному об'єкті `realm_access.roles`,
*а Spring Security за замовчуванням шукає їх в іншому місці. 
* Цей код витягує ролі з правильного місця.

-----

### \#\# Крок 5: Створення REST-контролера

Створіть пакет `controller`, а в ньому клас `DemoController.java`.

```java
package com.example.keycloakdemo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/public")
    public String getPublicData() {
        return "Це публічні дані, доступні для всіх!";
    }

    @GetMapping("/secured")
    public String getSecuredData(@AuthenticationPrincipal Jwt jwt) {
        // @AuthenticationPrincipal дозволяє отримати дані про поточного користувача з токену
        return "Це захищені дані. Ви увійшли як: " + jwt.getSubject();
    }
}
```

-----

### \#\# Крок 6: Тестування

1.  **Запустіть ваш Spring Boot додаток.**

2.  **Отримайте Access Token з Keycloak:**
    Відкрийте термінал і виконайте `curl` запит, підставивши свій `client-secret`.

    ```bash
    curl -X POST 'http://localhost:8180/realms/spring-demo-realm/protocol/openid-connect/token' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'grant_type=password' \
    --data-urlencode 'client_id=spring-boot-client' \
    --data-urlencode 'client_secret=ВАШ_СЕКРЕТ' \
    --data-urlencode 'username=testuser' \
    --data-urlencode 'password=password'
    ```

    Ви отримаєте JSON відповідь. Скопіюйте значення поля `access_token`.

3.  **Перевірте публічний ендпоінт (без токену):**

    ```bash
    curl http://localhost:8080/public
    ```

    **Очікуваний результат:** `Це публічні дані, доступні для всіх!`

4.  **Перевірте захищений ендпоінт (без токену):**

    ```bash
    curl -i http://localhost:8080/secured
    ```

    **Очікуваний результат:** `HTTP/1.1 401 Unauthorized`

5.  **Перевірте захищений ендпоінт (з токеном):**
    Замініть `<ВАШ_ТОКЕН>` на скопійований `access_token`.

    ```bash
    curl -H "Authorization: Bearer <ВАШ_ТОКЕН>" http://localhost:8080/secured
    ```

    **Очікуваний результат:** `Це захищені дані. Ви увійшли як: <ID користувача testuser>`

**Демонстрація готова\!** Ви успішно налаштували захист ендпоінту за допомогою Spring Security та Keycloak.
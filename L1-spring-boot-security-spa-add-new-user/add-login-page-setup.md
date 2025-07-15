
### \#\#\# Частина 1: Оновлення конфігурації Keycloak (Надання прав)

Щоб ваш Spring Boot додаток міг створювати користувачів, йому потрібен спеціальний дозвіл. Ми надамо його через окремого, "довіреного" клієнта з правами адміністратора.

1.  **Зайдіть в адмін-консоль Keycloak** (`http://localhost:8081`).
2.  Перейдіть у ваш realm **`my-prod-app-realm`**.
3.  **Створіть нового, конфіденційного клієнта для бекенду:**
    * Перейдіть у **Clients** -\> **Create client**.
    * **Client ID**: `backend-admin-client`.
    * **Client type**: `OpenID Connect`.
    * **Authorization**: Увімкніть `Client authentication`.
    * Натисніть **Save**.
4.  **Отримайте секрет клієнта:**
    * Перейдіть на вкладку **Credentials**.
    * Скопіюйте **Client secret**. Він знадобиться для `application.yml` вашого бекенду.
    * Valid redirect URIs - http://localhost:8080/login/oauth2/code/keycloak
5.  **Надайте права на управління користувачами (ОБОВ'ЯЗКОВО):**
    * Поставте галку **Service accounts roles** вашого нового клієнта (`backend-admin-client`).
      * Натисніть **Assign role**.
      * У фільтрі виберіть **Filter by clients**.
      * Знайдіть та додайте наступні ролі для клієнта:
      * `realm-management: manage-users` (дозволяє створювати та видаляти користувачів).
      * `realm-management: view-users`
      * `realm-management: query-users` (дозволяють шукати користувачів).

-----

### \#\#\# Частина 2: Оновлення Бекенду (Spring Boot API)

#### **Крок 1: Додайте залежність Keycloak Admin Client**

Відкрийте `pom.xml` і додайте цю залежність.

```xml
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-admin-client</artifactId>
    <version>25.0.0</version> 
</dependency>
```

#### **Крок 2: Оновіть `application.yml`**

Додайте новий блок конфігурації для адмін-клієнта.

```yaml
# ... існуючі налаштування spring.security ...

#Конфігурація для підключення до Keycloak Admin API
keycloak:
  admin-client:
    server-url: http://localhost:8081
    realm: my-prod-app-realm
    # Дані конфіденційного клієнта, який ми щойно створили
    client-id: backend-admin-client
    client-secret: СЕКРЕТ_З_KEYCLOAK
```

#### **Крок 3: Створіть сервіс для реєстрації (`RegistrationService`)**

Цей сервіс буде інкапсулювати всю логіку створення користувача в Keycloak.

```java
package com.example.prodreadyapi.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    @Value("${keycloak.admin-client.server-url}")
    private String serverUrl;
    @Value("${keycloak.admin-client.realm}")
    private String realm;
    @Value("${keycloak.admin-client.client-id}")
    private String clientId;
    @Value("${keycloak.admin-client.client-secret}")
    private String clientSecret;

    public void registerNewUser(String username, String password) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .grantType("client_credentials")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        user.setCredentials(List.of(credential));

        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.create(user);

        if (response.getStatus() != 201) {
            String errorMessage = response.readEntity(String.class);
            log.error("Не вдалося створити користувача: {}", errorMessage);
            throw new RuntimeException("Не вдалося створити користувача: " + errorMessage);
        }
        log.info("Користувача '{}' успішно створено.", username);
        
        // Автоматично присвоюємо роль 'app_user'
        String userId = usersResource.searchByUsername(username, true).get(0).getId();
        RoleRepresentation userRole = keycloak.realm(realm).roles().get("app_user").toRepresentation();
        usersResource.get(userId).roles().realmLevel().add(List.of(userRole));
        log.info("Роль 'app_user' присвоєно користувачу '{}'", username);
    }
}
```

#### **Крок 4: Створіть `RegistrationController`**

```java
package com.example.prodreadyapi.controller;

import com.example.prodreadyapi.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        registrationService.registerNewUser(request.username(), request.password());
        return ResponseEntity.ok("Користувача успішно зареєстровано! Тепер ви можете увійти.");
    }
    
    // Створіть простий DTO record для запиту
    public record RegistrationRequest(String username, String password) {}
}
```

#### **Крок 5: Оновіть `SecurityConfig`**

Дозвольте публічний доступ до нового ендпоінту реєстрації.

```java
// ... всередині SecurityFilterChain ...
.authorizeHttpRequests(authorize -> authorize
    .requestMatchers("/api/register").permitAll() // ✅ ДОДАЙТЕ ЦЕЙ РЯДОК
    .requestMatchers("/public").permitAll()
    .requestMatchers("/private").hasRole("app_user")
    .anyRequest().authenticated()
)
```

-----

### \#\#\# Частина 3: Оновлення Фронтенду (`index.html`)

Додамо форму реєстрації та логіку для її відправки.

```html
<!DOCTYPE html>
<html lang="uk">
<head>
    </head>
<body>
    <h1>Prod-Ready Демо</h1>
    <div>Статус: <span id="status">Ініціалізація...</span></div>
    <hr>
    <button id="loginBtn" onclick="keycloak.login()">Увійти</button>
    <a href="#" id="show-register-link" style="margin-left: 10px;">Зареєструватися</a>
    <button id="logoutBtn" onclick="keycloak.logout()">Вийти</button>
    <hr>

    <div id="register-zone" style="display:none; margin-bottom: 20px;">
        <h3>Створити новий акаунт</h3>
        <form id="register-form">
            <input type="text" id="reg-username" placeholder="Логін" required>
            <input type="password" id="reg-password" placeholder="Пароль" required>
            <button type="submit">Зареєструватися</button>
        </form>
    </div>

    <button id="apiBtn" onclick="callApi()">Викликати приватний ендпоінт (/private)</button>
    <div id="api-response" style="margin-top: 1em; font-weight: bold;"></div>
    <h3>Токен:</h3>
    <pre id="token-info">Немає токену.</pre>

<script>
    // ... існуючий JavaScript ...
    const registerZone = document.getElementById('register-zone');
    const registerForm = document.getElementById('register-form');
    const showRegisterLink = document.getElementById('show-register-link');
    const apiResponseDiv = document.getElementById('api-response');

    showRegisterLink.addEventListener('click', (e) => {
        e.preventDefault();
        registerZone.style.display = registerZone.style.display === 'none' ? 'block' : 'none';
    });

    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        apiResponseDiv.textContent = 'Реєструємо...';
        
        const username = registerForm['reg-username'].value;
        const password = registerForm['reg-password'].value;

        try {
            const response = await fetch('http://localhost:8080/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });
            
            const responseText = await response.text();
            if (!response.ok) throw new Error(responseText);
            
            apiResponseDiv.style.color = 'green';
            apiResponseDiv.textContent = responseText;
            registerZone.style.display = 'none';
        } catch (err) {
            apiResponseDiv.style.color = 'red';
            apiResponseDiv.textContent = 'Помилка реєстрації: ' + err.message;
        }
    });

    function updateUI(authenticated) {
        // ... існуюча логіка ...
        document.getElementById('show-register-link').style.display = authenticated ? 'none' : 'inline-block';
    }
</script>
</body>
</html>
```

Тепер ваш додаток має повноцінний функціонал самостійної реєстрації користувачів через ваш бекенд, який, у свою чергу, створює їх у Keycloak.
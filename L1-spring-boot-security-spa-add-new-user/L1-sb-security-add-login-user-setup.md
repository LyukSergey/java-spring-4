### **Побудова сучасної та безпечної архітектури з Spring Security та Keycloak**

#### *Архітектура нашого рішення**

Cкладається з трьох незалежних частин:

1.  **Front-End (ваш `index.html`):** Це SPA (Single Page Application), яке відповідає за UI. Його ключова риса — він є **публічним клієнтом** і ніколи не обробляє паролі напряму. 
Його завдання — ініціювати вхід, перенаправляючи користувача на Keycloak, 
а також відправляти запити на бекенд, додаючи до них отриманий токен.
2.  **Back-End (ваш проєкт `L1-spring-boot-security-spa-add-new-user`):** Це наш **stateless REST API**. 
Він виступає як:
    * **Сервер Ресурсів:** Перевіряє JWT-токени при кожному запиті до захищених ендпоінтів (`/private`).
    * **Адміністративний Клієнт:** Спілкується з Keycloak API для створення нових користувачів.
3.  **Keycloak**: Наш централізований **Сервер Авторизації**. Він керує базою користувачів, видає та перевіряє токени.

-----
### \#\#\# Встановіть http-server

* Запустіть сервер:
* http-server -p 3000
* 
* start http-server на порту 3000:
* http-server -p 3000 

* Відкрийте сторінку у браузері: Тепер відкрийте http://localhost:3000.

Цей сервер буде віддавати ваш HTML-файл "чистим", без жодних додаткових заголовків на кшталт x-ijt.
Idea додвє x-ijt заголовки які викликають помилки CORS, тому ми використовуємо http-server.

-----

### \#\#\# Налаштування Keycloak
Нам потрібно налаштувати в Keycloak **два різних клієнти**

#### **1: Конфігурація публічного клієнта для Front-End (`my-webapp-client`)**
Цей клієнт представляє ваш SPA-додаток.

* **Client ID**: `my-webapp-client`
* **Client authentication**: **`OFF`**.
Ми вимикаємо це, бо клієнт є **публічним**. 
Його код (JavaScript) виконується у браузері користувача, де неможливо безпечно зберігати `client secret`. 
Безпека цього клієнта покладається **не на секрет**, а на сувору перевірку **`Valid Redirect URIs`**.
* **Valid Redirect URIs**: `http://localhost:3000/*` (або порт вашого фронтенду).
* **Web Origins**: `http://localhost:3000`.

#### **Крок 2: Створення конфіденційного клієнта для Back-End (`backend-admin-client`)**

Цей клієнт буде використовуватися вашим Spring Boot додатком для адміністративних завдань (створення користувачів).

1.  **Створіть клієнт**: Перейдіть у `Clients` -\> `Create client`.
    * **Client ID**: `backend-admin-client`.
    * **Client authentication**: **`ON`**.
        * **Пояснення:** Ми вмикаємо це, бо клієнт є **конфіденційним**. 
        * Ваш бекенд працює на захищеному сервері і може безпечно зберігати `client secret` у файлі `application.yaml`.
    * **Service accounts roles**: Увімкніть цю опцію.
2.  **Налаштуйте права**:
    * Перейдіть на вкладку **Service account roles** цього клієнта.
    * Натисніть **Assign role** та, відфільтрувавши по клієнту `realm-management`, додайте ролі: 
    * `manage-users`, `view-users`, `query-users`, `view-realm`.
        * **Пояснення:** Ці ролі надають вашому бекенду права на виконання відповідних операцій через Keycloak Admin API. 
        * Без них спроба створити чи знайти користувача завершиться помилкою `403 Forbidden`.
        * - **manage-users**: Дозволяє створювати, редагувати та видаляти користувачів.
        * - **view-users**: Дозволяє переглядати користувачів.
        * - **query-users**: Дозволяє шукати користувачів.
        * - **view-realm**: Дозволяє читати інформацію про realm, наприклад, список ролей.
-----

### \#\#\# Частина 2: Налаштування вашого Back-End (`L1-spring-boot-security-spa-add-new-user`)

#### **Крок 1: Залежності у `pom.xml`**

У вашому файлі `pom.xml` є три ключові залежності для безпеки:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-admin-client</artifactId>
    <version>25.0.0</version>
</dependency>
```

* **`spring-boot-starter-security`**: Вмикає Spring Security.
* **`spring-boot-starter-oauth2-resource-server`**: Налаштовує додаток на прийом та валідацію JWT-токенів.
* **`keycloak-admin-client`**: Надає Java-класи для взаємодії з Keycloak API.

#### **Крок 2: Конфігурація в `application.yaml`**

`application.yaml` демонструє подвійну роль бекенду:

```yaml
# application.yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          # Частина 1: Налаштування для перевірки токенів
          issuer-uri: http://localhost:8081/realms/my-prod-app-realm
keycloak:
  admin-client:
    # Частина 2: Налаштування для адмін-доступу
    server-url: http://localhost:8081
    realm: my-prod-app-realm
    client-id: backend-admin-client
    client-secret: QCYjr2HHAath9oZj85QPlI3NB0jGSesg
```

* **`resourceserver.jwt.issuer-uri`**: Це **найважливіший параметр для захисту API**. 
Він каже Spring Security, хто є довіреним видавцем токенів. 
Spring автоматично завантажить звідси публічні ключі для перевірки підпису JWT.
* **`keycloak.admin-client`**: Це ваш кастомний блок конфігурації. 
Він містить облікові дані (`client-id` та `client-secret`) для **конфіденційного клієнта**, 
які будуть використовуватися для аутентифікації вашого бекенду в Keycloak для виконання адмін-операцій.

#### **Створіть клас `KeycloakProperties.java`**
```java
@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak.admin-client")
public class KeycloakProperties {

    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
}
```

#### **Створіть бін `KeycloakConfig.java`**
```java
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
```

#### **Створіть бін `KeycloakConfig.java`**
```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    private String username;
    private String email;
    private String password;

}
```




#### **Створіть клас `RegistrationService.java`**

```java
@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    private final KeycloakProperties keycloakProperties;
    private final Keycloak keycloak;

    public void registerNewUser(String username, String password, String email) {
        // 1. Створюємо представлення нового користувача
        final UserRepresentation user = createUserRepresentation(username, email);
        // 2. Створюємо представлення пароля
        final CredentialRepresentation credential = createCredentialRepresentation(password);
        user.setCredentials(List.of(credential));
        
        // 3. Отримуємо доступ до ресурсів користувачів нашого realm'у
        final UsersResource usersResource = createUsersResource();
        // 4. Отримуємо представлення ролі, яку будемо присвоювати
        final RoleRepresentation defaultRole = keycloak.realm(keycloakProperties.getRealm())
                .roles()
                .get("app_user")
                .toRepresentation();
                
        // 5. Виконуємо атомарну операцію створення користувача та присвоєння ролі
        createNewUserInKeycloacl(username, usersResource, user)
                .ifPresent(newKeycloackUser -> getUserRoles(usersResource, newKeycloackUser.getId(), newKeycloackUser.getUsername(), defaultRole));
    }

    // 6. Метод присвоєння ролі ролбеком у випадку ексепшину
    private static void getUserRoles(UsersResource usersResource, String newUserId, String username, RoleRepresentation defaultRole) {
        try {
            // Присвоюємо роль користувачу
            usersResource.get(newUserId)
                    .roles()
                    .realmLevel()
                    .add(List.of(defaultRole));
        } catch (Exception e) {
            // ролбек: якщо присвоїти роль не вдалося, видаляємо щойно створеного користувача.
            usersResource.get(newUserId).remove();
            throw new RuntimeException(String.format("Не вдалося створити користувача: %s", username), e);
        }
    }

    // 7. Метод для створення користувача через Keycloak API
    private static Optional<UserRepresentation> createNewUserInKeycloacl(String username, UsersResource usersResource, UserRepresentation user) {
        try (Response response = usersResource.create(user)) {
            if (response.getStatus() != 201) { // 201 Created
                String errorMessage = response.readEntity(String.class);
                log.error("Не вдалося створити користувача: {}", errorMessage);
                throw new RuntimeException("Не вдалося створити користувача: " + errorMessage);
            } else {
                // Повертаємо щойно створеного користувача для подальших операцій
                return Optional.ofNullable(usersResource.searchByUsername(username, true))
                        .orElse(List.of())
                        .stream()
                        .findFirst();
            }
        }
    }

    // 8. Метод для отримання ресурсу користувачів
    private UsersResource createUsersResource() {
        return Optional.ofNullable(keycloakProperties.getRealm())
                .map(keycloak::realm)
                .map(RealmResource::users)
                .orElseThrow(() -> new RuntimeException("Realm not found: " + keycloakProperties.getRealm()));
    }

    // 9. Метод для створення представлення пароля
    private static CredentialRepresentation createCredentialRepresentation(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

    // 10. Метод для створення представлення користувача
    private static UserRepresentation createUserRepresentation(String username, String email) {
        final UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
}
```

#### **Створіть `SecurityConfig.java`**

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Налаштування CORS для взаємодії з фронтендом
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // 2. Вимкнення CSRF, оскільки ми використовуємо stateless JWT
            .csrf(AbstractHttpConfigurer::disable)
            // 3. Налаштування правил доступу до ендпоінтів
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Дозволяємо preflight запити
                .requestMatchers("/register", "/public").permitAll() // Дозволяємо публічний доступ
                .requestMatchers("/private").hasRole("app_user") // Вимагаємо роль
                .anyRequest().authenticated()
            )
            // 4. Вмикаємо режим Сервера Ресурсів
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

    // ОБОВ'ЯЗКОВА КОНФІГУРАЦІЯ CORS
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

* **Пояснення:** Ця конфігурація чітко визначає, які ендпоінти є публічними, 
а які вимагають аутентифікації та певної ролі. 
Вона також вмикає механізм перевірки JWT (`.oauth2ResourceServer()`) 
і налаштовує CORS для взаємодії з фронтендом.

-----

### \#\#\# Частина 3: Пояснення вашого Front-End (`index.html`)

Ваш файл `index.html` ідеально демонструє логіку роботи SPA.

```html
<!DOCTYPE html>
<html lang="uk">
<head>
  <meta charset="UTF-8">
  <title>Spring Security & Keycloak Demo</title>
  <script src="http://localhost:8081/js/keycloak.js"></script>
  <style>
    body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif; padding: 2em; max-width: 900px; margin: auto; background-color: #f8f9fa; color: #343a40; }
    button { padding: 10px 15px; font-size: 16px; cursor: pointer; border: none; border-radius: 5px; margin: 5px 0; color: white; transition: background-color 0.2s ease; }
    pre { background-color: #e9ecef; padding: 1em; white-space: pre-wrap; word-wrap: break-word; border-radius: 5px; border: 1px solid #dee2e6; }
    .hidden { display: none; }
    .container { border: 1px solid #dee2e6; padding: 20px; margin-top: 15px; border-radius: 8px; background-color: white; }
    input { display: block; padding: 10px; margin-bottom: 10px; width: 95%; font-size: 16px; border: 1px solid #ced4da; border-radius: 4px; }
    h1, h2, h3 { color: #212529; }
    #loginBtn { background-color: #28a745; }
    #logoutBtn { background-color: #dc3545; }
    #apiBtn { background-color: #007bff; }
    .response-display { margin-top: 15px; font-weight: bold; padding: 10px; border-radius: 5px; }
    .success { color: #155724; background-color: #d4edda; border: 1px solid #c3e6cb; }
    .error { color: #721c24; background-color: #f8d7da; border: 1px solid #f5c6cb; }
  </style>
</head>
<body>
<h1>Демо-додаток з Keycloak</h1>
<div>Статус: <span id="status" style="font-weight: bold;">Ініціалізація...</span></div>
<hr>

<div id="logged-out-zone">
  <div class="container">
    <h3>Вхід та Реєстрація</h3>
    <button id="loginBtn" onclick="keycloak.login()">Увійти через Keycloak</button>
    <p>Немає акаунту? <a href="#" id="show-register-link">Створити зараз</a>.</p>
    <form id="register-form" class="hidden">
      <h4>Новий акаунт</h4>
      <input type="text" id="reg-username" placeholder="Бажаний логін" required>
      <input type="email" id="reg-email" placeholder="Ваш Email" required>
      <input type="password" id="reg-password" placeholder="Пароль" required>
      <button type="submit">Зареєструватися</button>
    </form>
  </div>
</div>

<div id="logged-in-zone" class="hidden">
  <h3>Приватна зона</h3>
  <button id="logoutBtn" onclick="keycloak.logout()">Вийти</button>
  <button id="apiBtn" onclick="callApi()">Викликати приватний ендпоінт</button>
  <h3>Ваш токен:</h3>
  <pre id="token-info">Немає токену.</pre>
</div>

<div id="response-display"></div>

<script>
  const keycloakConfig = {
    url: 'http://localhost:8081', // Перевірте ваш порт Keycloak
    realm: 'my-prod-app-realm',    // Перевірте назву вашого realm
    clientId: 'my-webapp-client'   // ID вашого ПУБЛІЧНОГО клієнта для фронтенду
  };
  const keycloak = new Keycloak(keycloakConfig);
  const apiBaseUrl = 'http://localhost:8080'; // Адреса вашого бекенду

  const loggedInZone = document.getElementById('logged-in-zone');
  const loggedOutZone = document.getElementById('logged-out-zone');
  const statusDiv = document.getElementById('status');
  const tokenInfoPre = document.getElementById('token-info');
  const responseDisplayDiv = document.getElementById('response-display');
  const registerForm = document.getElementById('register-form');
  const showRegisterLink = document.getElementById('show-register-link');

  // Ініціалізація Keycloak
  keycloak.init({ onLoad: 'check-sso' }).then(authenticated => {
    updateUI(authenticated);
  });

  function updateUI(authenticated) {
    loggedInZone.classList.toggle('hidden', !authenticated);
    loggedOutZone.classList.toggle('hidden', authenticated);
    statusDiv.textContent = authenticated ? `Аутентифікований як ${keycloak.tokenParsed.preferred_username}` : 'Не аутентифікований';
    tokenInfoPre.textContent = authenticated ? JSON.stringify(keycloak.tokenParsed, null, 2) : 'Немає токену.';
    responseDisplayDiv.textContent = '';
    responseDisplayDiv.className = 'response-display';
  }

  // Показ/приховування форми реєстрації
  showRegisterLink.addEventListener('click', (e) => {
    e.preventDefault();
    registerForm.classList.toggle('hidden');
  });

  // Обробник для форми реєстрації
  registerForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    displayResponse('Реєструємо користувача...', 'info');

    const username = document.getElementById('reg-username').value;
    const email = document.getElementById('reg-email').value;
    const password = document.getElementById('reg-password').value;

    try {
      const response = await fetch(`${apiBaseUrl}/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email, password })
      });

      const responseText = await response.text();
      if (!response.ok) throw new Error(responseText);

      displayResponse(responseText, 'success');
      registerForm.reset();
      registerForm.classList.add('hidden');
    } catch (error) {
      displayResponse(`Помилка реєстрації: ${error.message}`, 'error');
    }
  });

  // Функція для виклику захищеного API (залишається без змін)
  async function callApi() {
    displayResponse('Виконується запит до захищеного API...', 'info');
    try {
      const response = await fetch(`${apiBaseUrl}/private`, {
        headers: { 'Authorization': 'Bearer ' + keycloak.token }
      });
      if (!response.ok) throw new Error(`Помилка HTTP: ${response.status} ${response.statusText}`);
      const data = await response.text();
      displayResponse(`Відповідь від сервера: ${data}`, 'success');
    } catch (error) {
      displayResponse(`Помилка: ${error.message}`, 'error');
    }
  }

  // Допоміжна функція для відображення повідомлень
  function displayResponse(message, type) {
    responseDisplayDiv.textContent = message;
    responseDisplayDiv.className = `response-display ${type}`;
  }
</script>
</body>
</html>
```

* **`keycloak.init()`**: При завантаженні сторінки скрипт перевіряє, чи є активна сесія в Keycloak.
* **`keycloak.login()`**: Кнопка "Увійти" ініціює безпечний **Authorization Code Flow** з редіректом.
* **`registerForm.addEventListener`**: Форма реєстрації збирає дані і надсилає їх на бекенд, який вже сам спілкується з Keycloak.
* **`callApi()`**: Функція демонструє, як Access Token, отриманий від Keycloak, додається до кожного запиту на захищений ресурс.
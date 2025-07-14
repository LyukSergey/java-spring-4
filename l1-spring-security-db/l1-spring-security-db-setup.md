-----

### \#\# Крок 1: Створення проєкту та залежності

1.  Перейдіть на `start.spring.io`.
2.  Створіть новий проєкт з наступними залежностями:
    * **Spring Web**: для створення вебконтролерів.
    * **Spring Security**: для налаштування безпеки.
    * **Spring Data JPA**: для роботи з базою даних.
    * **Thymeleaf**: для створення HTML-сторінок.
    * **PostgreSql**: БД.

-----

### \#\# Крок 2: Налаштування бази даних

Відкрийте файл `src/main/resources/application.properties` і додайте налаштування для H2 бази даних:

```properties
  # PostgreSQL DataSource
datasource:
    url: jdbc:postgresql://localhost:5432/l1-spring-security_db
    username: demo_user
    password: demo_password
    driver-class-name: org.postgresql.Driver
# JPA / Hibernate
# Вказуємо Spring, що ми працюємо з PostgreSQL
    jpa:
        hibernate:
            ddl-auto: update
# Властивості, що передаються напряму в Hibernate
        properties:
            hibernate:
# Явно вказуємо, що ми працюємо з діалектом PostgreSQL
                dialect: org.hibernate.dialect.PostgreSQLDialect
        show-sql: true
        open-in-view: falsee
```

-----

### \#\# Крок 3: Створення моделі та репозиторію користувача

#### **3.1. Сутність `User`**

Створіть клас `User.java` для представлення користувача в базі даних.

```java
package com.example.dbsecurity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
}
```

#### **3.2. Репозиторій `UserRepository`**

Створіть інтерфейс для роботи з таблицею користувачів.

```java
package com.example.dbsecurity.repository;

import com.example.dbsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

-----

### \#\# Крок 4: Реалізація `UserDetailsService`

Це "місток" між вашою моделлю `User` та `UserDetails` від Spring Security.

#### **4.1. Створіть клас `SecurityUser`**

Це клас-обгортка, який реалізує інтерфейс `UserDetails`.

```java
package com.example.dbsecurity.security;

import com.example.dbsecurity.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    // ... інші методи інтерфейсу (isAccountNonExpired і т.д. можна залишити true)
}
```

#### **4.2. Створіть сервіс `JpaUserDetailsService`**

Цей сервіс буде завантажувати користувача з бази даних.

```java
package com.example.dbsecurity.service;

import com.example.dbsecurity.repository.UserRepository;
import com.example.dbsecurity.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
```

-----

### \#\# Крок 5: Налаштування `SecurityFilterChain`

Це головний клас для конфігурації безпеки.

```java
package com.example.dbsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
```

-----

### \#\# Крок 6: Створення контролерів та сторінок

#### **6.1. Контролер**

Створіть `PageController.java` для відображення сторінок.

```java
package com.example.dbsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String getPublicPage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String getPrivatePage() {
        return "home";
    }
}
```

#### **6.2. Сторінка логіну (`src/main/resources/static/style.css`)**

```css
body {
font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
background-color: #f4f4f4;
margin: 0;
display: flex;
flex-direction: column;
align-items: center;
min-height: 100vh;
padding-top: 20px;
}

h1, h2 {
color: #333;
text-align: center;
margin-bottom: 20px;
}

form {
background-color: #fff;
padding: 30px;
border-radius: 8px;
box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
width: 400px;
max-width: 90%;
}

label {
display: block;
margin-bottom: 10px;
color: #555;
font-weight: bold;
}

input {
width: 100%;
padding: 10px;
margin-bottom: 15px;
border: 1px solid #ddd;
border-radius: 4px;
box-sizing: border-box;
font-size: 16px;
}

button, input [type="submit"] {
background-color: #007bff;
color: white;
padding: 12px 20px;
border: none;
border-radius: 4px;
cursor: pointer;
font-size: 16px;
transition: background-color 0.3s ease;
}

button:hover, input [type="submit"]:hover {
background-color: #0056b3;
}

.error-message {
color: #dc3545;
margin-bottom: 15px;
text-align: center;
}

p {
color: #666;
text-align: center;
margin-top: 20px;
}
```


#### **6.2. Сторінка логіну (`src/main/resources/templates/login.html`)**

```html
<!DOCTYPE html>
<html lang="uk" xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" th:href="@{/css/style.css}">
</head>
<body>
    <h1>Сторінка входу</h1>
    <div th:if="${param.error}" style="color: red;">
        Неправильне ім'я користувача або пароль.
    </div>
    <form th:action="@{/login}" method="post">
        <div><label> Ім'я користувача: <input type="text" name="username"/> </label></div>
        <div><label> Пароль: <input type="password" name="password"/> </label></div>
        <div><input type="submit" value="Увійти"/></div>
    </form>
</body>
</html>
```

#### **6.3. Сторінка після успішного входу (`src/main/resources/templates/home.html`)**

```html
<!DOCTYPE html>
<html lang="uk" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" th:href="@{/css/style.css}">
</head>
<body>
    <h1>Вітаємо на захищеній сторінці!</h1>
    <p>Ви увійшли як: <b sec:authentication="name"></b></p>
    <form th:action="@{/logout}" method="post">
        <button type="submit">Вийти</button>
    </form>
</body>
</html>
```

-----

### \#\# Крок 7: Додавання тестового користувача

Щоб можна було увійти, створіть тестового користувача при старті додатку.

```java
package com.example.dbsecurity;

import com.example.dbsecurity.model.User;
import com.example.dbsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("password")); // Шифруємо пароль
        adminUser.setRole("ROLE_ADMIN"); // Ролі мають починатися з "ROLE_"
        userRepository.save(adminUser);
    }
}
```

Тепер запустіть додаток, перейдіть на `http://localhost:8080/login` та увійдіть з логіном `admin` та паролем `password`. Вас має успішно перенаправити на сторінку `/home`.
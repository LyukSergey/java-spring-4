### **Покрокова інструкція до домашнього завдання: Розширення Bank API**

Кожен створює свю гілку feature/name_surname
 
Ми пройдемося по кожному шару архітектури: від бази даних до кінцевого REST-ендпоінту.

**Передумови:** Ви починаєте з проєкту, де вже є налаштовані сутності `Bank` та `User` 
зі зв'язком `OneToMany`, відповідні репозиторії та сервіс для реєстрації нового користувача.

-----

### **Частина 1: Реалізація `GET /banks/{bankId}/users` (Отримання користувачів банку)**

**Мета:** Створити ендпоінт, який повертає список усіх користувачів, що належать конкретному банку.

#### **Крок 1: Рівень Репозиторію — Навчаємо `UserRepository` шукати за банком**

1.  Відкрийте файл інтерфейсу `UserRepository.java`.

2.  Додайте в нього новий метод. Вам не потрібно писати реалізацію, Spring Data JPA зробить це за вас, проаналізувавши назву методу.

    ```java

    public interface UserRepository extends JpaRepository<User, Long> {
        // ✅ ДОДАЙТЕ ЦЕЙ РЯДОК
        List<User> findAllByBankId(Long bankId);
    }
    ```

    * **Пояснення:** Назва методу `findAllByBankId` чітко каже Spring Data JPA: 
    * "знайди всі (`findAll`) сутності `User`, у яких поле `bank`, 
    * а в ньому поле `id`, дорівнює переданому параметру `bankId`".

#### **Крок 2: Рівень Сервісу — Створюємо метод для бізнес-логіки**

1.  Відкрийте клас `BankManagementService.java`.

2.  Додайте новий публічний метод, який буде викликати репозиторій.

    ```java
    // ... інші імпорти
    import com.lss.l6springboot.entity.User;
    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class BankManagementService {
        // ... існуючі поля

        // ... існуючий метод registerNewUser

        // ✅ ДОДАЙТЕ ЦЕЙ МЕТОД
        public List<User> getUsersByBank(Long bankId) {
            return userRepository.findAllByBankId(bankId);
        }
    }
    ```

#### **Крок 3: Рівень Контролера — Створюємо публічний ендпоінт**

1.  Відкрийте ваш REST-контролер (наприклад, `BankController.java`).

2.  Додайте новий метод для обробки GET-запитів.

    ```java
    // ... інші імпорти
    import com.lss.l6springboot.entity.User;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import java.util.List;

    @RestController
    @RequiredArgsConstructor
    public class BankController {
        // ... існуюче поле та методи

        // ✅ ДОДАЙТЕ ЦЕЙ МЕТОД
        @GetMapping("/banks/{bankId}/users")
        public ResponseEntity<List<User>> getUsersByBank(@PathVariable Long bankId) {
            // ✅ ДОДАЙТЕ ЛОГІКУ ДЛЯ ОТРИМАННЯ КОРИСТУВАЧІВ
        }
    }
    ```

    * **Пояснення:**
        * `@GetMapping("/banks/{bankId}/users")`: Мапить HTTP GET-запити на цей метод. 
        * `{bankId}` — це змінна частина URL.
        * `@PathVariable Long bankId`: Вказує Spring, що потрібно взяти значення зі змінної `{bankId}` 
        * в URL і передати його в параметр методу `bankId`.

#### **Крок 4: Перевірка**

1.  Запустіть ваш Spring Boot додаток.
2.  Відкрийте браузер або інструмент для тестування API (наприклад, Postman) і перейдіть за адресою `http://localhost:8080/banks/1/users` (замість `1` підставте ID існуючого банку).
3.  **Очікуваний результат:** Ви повинні побачити JSON-масив з об'єктами користувачів, які належать цьому банку.

-----

### **Частина 2: Реалізація `DELETE /users/{userId}` (Видалення користувача)**

**Мета:** Створити ендпоінт для видалення користувача за його унікальним ідентифікатором.

#### **Крок 1: Рівень Репозиторію — Нічого не робимо\!**

Інтерфейс `JpaRepository` вже надає нам потрібний метод `deleteById(ID id)`. 
Тому файл `UserRepository.java` змінювати не потрібно.

#### **Крок 2: Рівень Сервісу — Додаємо логіку видалення**

1.  Поверніться до класу `UserService.java`.

2.  Додайте новий метод `deleteUser`. Оскільки це операція, що змінює дані, її варто позначити анотацією `@Transactional`.

    ```java
    // ... всередині класу BankManagementService ...

    import org.springframework.transaction.annotation.Transactional;

    // ✅ ДОДАЙТЕ ЦЕЙ МЕТОД
    @Transactional
    public void deleteUser(Long userId) {
        // Тут можна додати перевірку на існування користувача, якщо потрібно
        // наприклад, if (!userRepository.existsById(userId)) { throw ... }
        userRepository.deleteById(userId);
    }
    ```

#### **Крок 3: Рівень Контролера — Створюємо ендпоінт для видалення**

1.  Відкрийте ваш REST-контролер (`UserController.java`).

2.  Додайте метод для обробки HTTP DELETE-запитів.

    ```java
    // ... всередині класу UserController ...

    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.PathVariable;

    // ✅ ДОДАЙТЕ ЦЕЙ МЕТОД
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        bankService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    ```

    * **Пояснення:**
        * `@DeleteMapping("/users/{userId}")`: Мапить HTTP DELETE-запити.
        * `ResponseEntity.noContent().build()`: Це стандартна практика для DELETE-запитів. 
        * Вона повертає клієнту відповідь з HTTP-статусом **204 No Content**, 
        * що означає "Операція успішна, але у відповіді немає тіла".

#### **Крок 4: Перевірка**

1.  Перезапустіть додаток.
2.  Оскільки браузер не може легко надсилати DELETE-запити, використайте Postman або команду `curl` у терміналі.

    **Приклад з `curl`:**
    ```bash
    curl -X DELETE http://localhost:8080/users/1
    ```

    (замініть `1` на ID користувача, якого хочете видалити).

3.  **Очікуваний результат:** Команда виконається без помилок і поверне пусту відповідь зі статусом 204. Щоб переконатися, що користувач видалений, ви можете знову виконати GET-запит з першого завдання і побачити, що цього користувача більше немає у списку.

Додайте файли до індексу git:
git add mvnw mvnw.cmd .mvn/

Зробіть коміт:
git commit -m "Add Maven Wrapper files"

Відправте зміни на сервер:
git push


git config --global user.name "Dubovyk Vladyslav"
git config --global user.email "wvladowv@gmail.com"
git commit -m "Add Maven Wrapper files"
git push --set-upstream origin feature/vladyslav_dubovyk
git push
 ### **1: Структура проекту**

* **Controller (`BankController.java`)**:
Приймає HTTP-запити (наприклад, від браузера чи іншого сервісу), 
розпаковує їх і передає команду на наступний шар.
**Серіалізація** — це процес перетворення об'єкта з його стану в пам'яті у формат, який можна зберегти або передати (JSON, XML).
**Десеріалізація** — це відновлення оригінального об'єкта з послідовності байтів (з файлу або мережевого запиту).
 
* **Service (`BankManagementServiceImpl.java`)**: 
Тут зосереджена вся бізнес-логіка: розрахунки, перевірки, координація роботи з даними.
* **Repository (`UserRepository.java`, `BankRepository.java`)**:Взаємодія з базою даних.

-----

### **2: Модель даних та її зв'язки**
Маємо дві сутності: `Bank` та `User`.

**`Bank.java`**

```java
package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity // 1. Це клас, що відповідає таблиці в БД
@Table(name = "bank") // 2. Явнa назва таблиці
@Getter
@Setter
public class Bank {
    @Id // 3. Первинний ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 4. Генерація ID на боці БД
    private Long id;

    private String name;
    
    @Column(name = "total_amount")
    private double totalAmount;

    // 5. Зв'язок "Один до Багатьох"
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();
}
```

1.  `@Entity`: Вона говорить JPA (і Hibernate під капотом), що цей Java-клас є сутністю і його екземпляри потрібно зберігати в базі даних.
2.  `@Table(name = "bank")`: JPA може автоматично визначити назву таблиці з назви класу, явно вказувати її — це хороша практика, яка уникає неоднозначностей.
3.  `@Id`: Позначає поле, яке є первинним ключем (`PRIMARY KEY`) для цієї таблиці.
4.  `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Доручає генерацію значення `id` самій базі даних (наприклад, через `AUTO_INCREMENT` в MySQL або `SERIAL` в PostgreSQL). 
Це найефективніший спосіб для більшості випадків.
5.  `@OneToMany`: Встановлює зв'язок "Один Банк -\> Багато Користувачів".
    * `mappedBy = "bank"`: **Важливий атрибут\!** Він каже JPA: "Я не є власником цього зв'язку. Інформація про зв'язок (зовнішній ключ) знаходиться в полі `bank` на стороні сутності `User`". 
    Це запобігає створенню зайвої проміжної таблиці.
    * `cascade = CascadeType.ALL`: **Принцип доміно.** Будь-яка операція (збереження, оновлення, видалення), що виконується над Банком, буде каскадно застосована до всіх пов'язаних з ним Користувачів. 
    Видалили банк — видалилися і всі його користувачі.
    * `orphanRemoval = true`: **"Контроль за сиротами"**. 
    Якщо ви видалите користувача зі списку `users` у об'єкта `Bank` і збережете цей банк, 
    JPA автоматично видалить цього користувача з бази даних.

**`User.java` - "Багато"**

```java
package com.example.demo.entity;

import jakarta.persistence.*;
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

    private String name;
    private String surname;

    // Зв'язок "Багато до Одного"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id") // Власник зв'язку
    private Bank bank;
}
```

* `@ManyToOne`: Встановлює зворотний зв'язок "Багато Користувачів -\> Один Банк". Клас, що містить `@ManyToOne`, є **власником** зв'язку.
* `@JoinColumn(name = "bank_id")`: Явно вказує, що в таблиці `users` буде стовпець `bank_id`, який є зовнішнім ключем (`FOREIGN KEY`) до таблиці `bank`.
* `fetch = FetchType.LAZY`: **Стратегія завантаження "Лінива"**. 
Це одна з найважливіших концепцій для оптимізації. 
Вона означає: "Коли ти завантажуєш `User` з бази даних, **не завантажуй** пов'язаний з ним об'єкт `Bank` одразу. 
Завантаж його лише тоді, коли хтось явно викличе `user.getBank()`". 
За замовчуванням для `@ManyToOne` використовується `EAGER`.

-----

### **3: `EAGER` vs `LAZY` та `LazyInitializationException`**

* **`EAGER` (Жадібне завантаження):** Завантажує пов'язані сутності одразу разом з основною.

    * **Аналогія:** Ви замовляєте піцу, і вам одразу привозять не тільки піцу, а й напої, соуси та десерт, навіть якщо ви їх ще не просили.
    * **Проблема:** Це може призводити до завантаження величезних об'ємів даних і проблеми "N+1 select",
        коли для завантаження N сутностей виконується N+1 запит до БД.

* **`LAZY` (Ліниве завантаження):** Завантажує пов'язані сутності лише за вимогою.

    * **Аналогія:** Вам привозять піцу. Коли ви захочете напій, ви робите окремий дзвінок, і вам його привозять.
    * **Перевага:** Значно ефективніше, завантажуються лише потрібні дані.

**`LazyInitializationException`?**

Це виняток, який говорить: 
"Ви намагаєтеся отримати "ліниві" дані, але сесія Hibernate (зв'язок з базою даних) вже закрита\!".

У нашому проєкті в `application.yaml` є ключова властивість:

```yaml
spring:
  jpa:
    open-in-view: false
```

* `open-in-view: false`: Ця властивість каже Spring: 
* "Відкривай сесію Hibernate тільки на час виконання методу репозиторію. Як тільки метод завершився — одразу закривай її". 
Це **рекомендована практика** для уникнення проблем з продуктивністю.

1.  Ваш сервіс викликає `userRepository.findById(1L)`. 
    Відкривається сесія, завантажується `User` (але не його `Bank`, бо він `LAZY`). 
    Метод завершується, сесія закривається.
2.  Далі в коді сервісу, вже **поза межами транзакції**, 
    ви намагаєтеся звернутися до банку користувача: `user.getBank().getName()`.
3.  Hibernate намагається виконати запит до БД, щоб завантажити банк, але сесія вже закрита.
    **Ви отримуєте `LazyInitializationException`**.

**Як це вирішити?** 
Позначити метод сервісу, який працює з лінивими даними, анотацією `@Transactional`.
`@Transactional` - найкрща практика анотувати методт, а не класи.
Так як тразакція дорога, то краще обмежити її межі.
`@Transactional` - можна ставити лише над public методами. Чому?

-----

### **4: Spring Data JPA — Репозиторії**

```java
package com.example.demo.repository;

import com.example.demo.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByBankId(Long bankId);
}
```

Ми не пишемо реалізацію. 
Spring Data JPA під час запуску додатку аналізує цей інтерфейс і автоматично створює бін, 
який реалізує всі стандартні CRUD-операції (`save`, `findById`, `findAll`, `deleteById` і т.д.).
Він парсить назви методів. Метод `findAllByBankId` Spring розуміє як: 
"Знайди (`find`) Усіх (`All`) за (`By`) полем пов'язаної сутності `bank` та її полем `Id`". 
Це називається **Query Methods**.

-----

### **5: Коли Магії Недостатньо — Анотація `@Query`**

Хоча Query Methods — це потужний інструмент, іноді нам потрібен повний контроль над запитом. 
Наприклад, для складних об'єднань, агрегацій або коли назва методу стає занадто довгою та нечитабельною.

Тут на допомогу приходить анотація `@Query`. 
Вона дозволяє нам писати запити власноруч, використовуючи два підходи: **HQL** та **Native SQL**.

#### **Підхід 1: HQL (Hibernate Query Language)**

HQL (або його сучасний аналог JPQL) — це об'єктно-орієнтована мова запитів. 
**Ключова відмінність:** ви оперуєте не назвами таблиць і стовпців, 
а **назвами класів-сутностей (`Entity`) та їх полів**. 
Це робить ваші запити незалежними від конкретної реалізації схеми БД.

**Приклад:** Знайдемо всіх користувачів з певним іменем.

**`UserRepository.java`**

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // ... існуючий метод findAllByBankId ...

    // ✅ ЗАПИТ ЧЕРЕЗ HQL
    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findUsersByNameHQL(@Param("name") String name);
}
```

* **`SELECT u FROM User u`**: Зверніть увагу, ми пишемо `FROM User`, 
де `User` — це **назва класу**, а не `FROM users` (назва таблиці). 
`u` — це псевдонім (alias).
* **`u.name`**: Ми посилаємося на **поле** `name` в класі `User`.
* **`:name`**: Це іменований параметр. 
За допомогою анотації `@Param("name")` ми зв'язуємо його зі змінною `name` нашого методу.

#### **Підхід 2: Native SQL**

Іноді нам потрібні специфічні можливості конкретної бази даних (наприклад, функції PostgreSQL, які не підтримуються в стандартному SQL) 
або дуже складні запити, які легше написати "чистим" SQL.

**Приклад:** Зробимо те саме, але за допомогою нативного SQL-запиту.

**`UserRepository.java`**

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // ... існуючі методи ...

    // ✅ НАТИВНИЙ SQL-ЗАПИТ
    @Query(value = "SELECT * FROM users u WHERE u.name = :name", nativeQuery = true)
    List<User> findUsersByNameNativeSQL(@Param("name") String name);
}
```

* **`value = "SELECT * FROM users ..."`**: 
Тут ми пишемо стандартний SQL. 
Зверніть увагу: `FROM users` — це вже **назва таблиці** з бази даних, а не класу.
* **`nativeQuery = true`**: **Важливий атрибут\!** Саме він каже Spring Data JPA, що це не HQL, 
і його потрібно виконувати без будь-яких перетворень. 
Якщо ви забудете цей прапорець, ви отримаєте помилку, оскільки Hibernate спробує розпарсити ваш SQL як HQL.

-----

### **6: Підготовка бази даних з Liquibase**

Щоб наш додаток працював, йому потрібні таблиці в базі даних. 
Використовувати `ddl-auto: create` — погана ідея для продакшену. 
Нам потрібен контроль над змінами схеми БД. 
Тут на допомогу приходить **Liquibase**.

Ми описуємо структуру БД у XML-файлах, які називаються "чейнджлогами".

1.  **`db.changelog-master.xml`**: Головний файл, який включає в себе інші.
2.  **`bank.xml`**: Створює таблицю `bank`.
3.  **`user.xml`**: Створює таблицю `users` та зовнішній ключ.
4.  **`test-data.xml`**: Цей файл наповнює наші таблиці тестовими даними за допомогою тегів `<insert>`. 
Це дозволяє нам мати готове середовище для розробки та тестування одразу після запуску.

Spring Boot автоматично підхоплює Liquibase, якщо бачить його в залежностях (`pom.xml`), 
знаходить головний чейнджлог, вказаний в `application.yaml`, 
і застосовує всі зміни до бази даних перед тим, як основний додаток почне працювати.

-----

### **7: Збираємо все до купи — `Service` та `Controller`**

**Сервіс (`BankManagementServiceImpl.java`)**

```java
@Service
@RequiredArgsConstructor
public class BankManagementServiceImpl implements BankManagementService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // ...

    @Override
    @Transactional // Гарантує, що сесія буде відкрита протягом всього методу
    public List<UserDto> getUsersByBank(Long bankId) {
        final List<User> users = userRepository.findAllByBankId(bankId);
        // Тут ми можемо безпечно звертатися до лінивих полів, якби вони нам були потрібні
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    // ...
}
```

**Контролер (`BankController.java`)**

```java
@RestController
@RequestMapping("/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankManagementService bankService;

    // ...

    @GetMapping("/{bankId}/users")
    public ResponseEntity<List<UserDto>> getUsersByBank(@PathVariable Long bankId) {
        return ResponseEntity.ok(bankService.getUsersByBank(bankId));
    }
}
```

Контролер приймає запит, витягує `bankId` з URL і просто делегує роботу сервісу. 
Сервіс, в свою чергу, звертається до репозиторію, отримує дані і перетворює їх у DTO (Data Transfer Object) 
для відправки клієнту. Все чітко і за правилами.

### **8: Завдання**
Реалізуйте метод у UserRepository:
Як знайти користувача з найдовшим прізвищем? 
Можна витягнути всіх користувачів банку і відсортувати їх у Java-коді. 
Але це неефективно! Краще зробити це на рівні бази даних.

SELECT
b.name AS bank_name,
COUNT(u.id) AS user_count
FROM
users u
JOIN
bank b ON u.bank_id = b.id
GROUP BY
b.name
ORDER BY
user_count DESC;
# Лекція: Все про Spring Boot — від Основ до Продакшену

---

# План Лекції

- Вступ: Що таке Spring Boot і чому він такий популярний?
- Фундамент Spring: IoC, DI, Біни та Контекст.
- Магія Spring Boot: Автоконфігурація, Стартери та процес запуску.
- Архітектура: Тришарова модель (Controller, Service, Repository).
- Практика: Побудова моделі даних, міграції, реалізація шарів.
- Транзакції: Як @Transactional гарантує цілісність даних.
- Тестування: Забезпечення якості коду.
- Бонус: Як працюють мета-анотації.

---

# Вступ до Spring Boot

Spring Boot — це ціла філософія, яка спрощує створення потужних, готових до продакшену додатків. Його основна мета — максимально прискорити процес розробки та позбавити вас від рутинної конфігурації.

- Швидкий старт: Створення проєкту за лічені хвилини.
- Автоконфігурація: Spring Boot "вгадує", що вам потрібно, і автоматично налаштовує багато речей.
- Вбудовані сервери: Tomcat, Jetty або Undertow вже є всередині.
- Production-ready: Моніторинг, метрики та конфігурація доступні одразу.

---

# Фундамент Spring: IoC, DI, Біни та Контекст

- IoC (Інверсія Управління): Це принцип, за яким контроль над створенням об'єктів передається фреймворку.
- DI (Впровадження Залежностей): Це механізм, за допомогою якого контейнер надає залежності об'єктам. Найкраща практика — через конструктор.
- Бін: Це будь-який об'єкт, життєвим циклом якого керує Spring.
- Контекст: Це і є сам IoC-контейнер. Фабрика, яка створює, налаштовує та зв'язує біни між собою.

---

# Магія Spring Boot: Автоконфігурація

Це процес, за допомогою якого Spring Boot автоматично налаштовує ваш додаток на основі JAR-файлів у вашому проєкті.

- Аналіз Classpath: Spring Boot дивиться, які бібліотеки ви додали.
- Умовна конфігурація: Спеціальні конфігурації активуються, лише якщо знайдено певний клас.
- Створення бінів: Якщо умова виконується, Spring Boot створює потрібні біни.

---

# Приклади Стартерів

- spring-boot-starter-web: Приносить все для створення вебдодатків: Spring MVC, вбудований Tomcat, бібліотеку для роботи з JSON.
- spring-boot-starter-data-jpa: Додає Spring Data JPA та Hibernate, що дозволяє легко працювати з базами даних.

---

# Глибоке Занурення: Як Запускається Додаток

Spring Boot створює "fat" JAR зі специфічною структурою.

- META-INF/MANIFEST.MF: Містить точку входу.
- org/springframework/boot/loader/: Внутрішній "двигун" для запуску (JarLauncher).
- BOOT-INF/classes/: Ваш скомпільований код.
- BOOT-INF/lib/: Усі ваші залежності.
  Процес запуску `java -jar`:

<!-- end list -->

1.  JVM запускає JarLauncher, вказаний у Main-Class маніфесту.
2.  JarLauncher створює кастомний завантажувач класів, який "бачить" залежності всередині BOOT-INF/lib/.
3.  JarLauncher знаходить ваш головний клас (вказаний у Start-Class маніфесту) і викликає його метод main().
4.  Ваш код викликає SpringApplication.run(), що запускає створення контексту та автоконфігурацію.

---

# Архітектура "Великої Картини"

Типовий додаток дотримується тришарової архітектури.

- Шар Представлення (Controller): Приймає HTTP-запити та повертає відповіді.
- Шар Бізнес-Логіки (Service): Містить основну бізнес-логіку програми.
- Шар Доступу до Даних (Repository): Відповідає за взаємодію з базою даних.

---

# Практика: Модель Даних (Bank.java)

```java
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double totalAmount;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();
}
```

---

# Практика: Модель Даних (User.java)

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;
}
```

---

# Міграції Бази Даних з Liquibase

Опишемо структуру таблиць у файлі db.changelog-master.xml.

```xml
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                   http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd">

    <changeSet id="1" author="your_name">
        <createTable tableName="bank">
            <column name="id" type="BIGINT" autoIncrement="true" primaryKey="true"/>
            <column name="name" type="VARCHAR(255)"/>
            <column name="total_amount" type="DOUBLE"/>
        </createTable>
    </changeSet>

    <changeSet id="2" author="your_name">
        <createTable tableName="users">
            <column name="id" type="BIGINT" autoIncrement="true" primaryKey="true"/>
            <column name="name" type="VARCHAR(255)"/>
            <column name="surname" type="VARCHAR(255)"/>
            <column name="bank_id" type="BIGINT"/>
        </createTable>
    </changeSet>

    <changeSet id="3" author="your_name">
        <addForeignKeyConstraint baseTableName="users" baseColumnNames="bank_id"
                                 constraintName="fk_users_bank"
                                 referencedTableName="bank" referencedColumnNames="id"/>
    </changeSet>
</databaseChangeLog>
```

---

# Реалізація Шару Контролера

Контролер обробляє вебзапити.

```java
@RestController
@RequiredArgsConstructor
public class IronBankController {
    private final BankManagementService bankService;

    @PostMapping("/users/register")
    public User registerUser(@RequestBody RegisterUserRequest request) {
        return bankService.registerNewUser(
            request.getName(), request.getSurname(), request.getBankId()
        );
    }
}
```

- @RestController: Кожен метод повертає дані (JSON) напряму.
- @PostMapping: Прив'язує HTTP POST запит до методу.
- @RequestBody: Перетворює тіло JSON-запиту в Java-об'єкт.

---

# Методи REST API: Шпаргалка

## | Метод | Призначення | Ідемпотентність | | :--- | :--- | :--- | | GET | Отримання даних | Так | | POST | Створення нового ресурсу | Ні | | PUT | Повне оновлення ресурсу | Так | | PATCH| Часткове оновлення ресурсу | Ні | | DELETE| Видалення ресурсу | Так |

# Шар Сервісу та Транзакції

Сервіс містить бізнес-логіку та гарантує цілісність даних за допомогою @Transactional.

```java
@Service
@RequiredArgsConstructor
public class BankManagementService {
    private final BankRepository bankRepository;
    private final UserRepository userRepository;

    @Transactional
    public User registerNewUser(String name, String surname, Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow();
        bank.setTotalAmount(bank.getTotalAmount() - 10.0);
        User newUser = new User(name, surname, bank);
        return userRepository.save(newUser);
    }
}
```

---

# Глибоке Занурення: Як Працює @Transactional

Призначення: Гарантувати атомарність операцій ("все або нічого").
Принцип дії (AOP + Проксі):

1.  Створення Проксі: Spring бачить @Transactional і створює проксі-об'єкт, який "огортає" ваш сервіс.
2.  Перехоплення виклику: Ваш виклик методу перехоплюється проксі.
3.  Логіка проксі:

<!-- end list -->

- До виклику методу: Проксі відкриває нову транзакцію в базі даних.
- Виклик оригінального методу: Проксі викликає ваш реальний метод.
- Після завершення:
- Успіх: Проксі надсилає команді COMMIT.
- Помилка: Проксі надсилає команді ROLLBACK.

---

# Гарантія Якості: Тестування

- Юніт-тести: Тестують маленькі частини коду ізольовано.
- Інтеграційні тести: Перевіряють взаємодію кількох компонентів.

<!-- end list -->

```java
@SpringBootTest
class ApplicationTests {
    @Test
    void contextLoads() {
        // Цей тест перевіряє, чи успішно завантажується контекст.
    }
}
```

---

# Бонус: Мета-анотації

Секрет "магії" Spring Boot — у мета-анотаціях.
@SpringBootApplication — це композитна анотація, яка включає в себе:

- @SpringBootConfiguration
- @EnableAutoConfiguration
- @ComponentScan
  Для їх створення використовуються стандартні мета-анотації Java:
- @Retention(RUNTIME): Анотація доступна під час виконання.
- @Target(TYPE): Анотацію можна застосувати до класу/інтерфейсу.

---

# Підсумки та Ключові Висновки

- Spring Boot спрощує розробку завдяки автоконфігурації та стартерам.
- В основі лежать принципи IoC та DI, реалізовані через ApplicationContext.
- Тришарова архітектура допомагає структурувати код.
- Spring Data JPA та Liquibase спрощують роботу з даними та міграціями.
- @Transactional гарантує цілісність даних за допомогою AOP-проксі.
- Спеціальна структура "fat" JAR та JarLauncher дозволяють легко запускати додаток.

---

# Запитання?

Дякую за увагу\!
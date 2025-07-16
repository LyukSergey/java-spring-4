### **1: Архітектура Тестування в Spring Boot**

-----

### **2: Оркестрація збірки та тестів за допомогою Maven (`pom.xml`)**

**Мета**: Повна автоматизація та ізоляція тестового середовища.

1.  **Розділення тестів**:

    * `maven-surefire-plugin`: Відповідає за **unit-тести** (класи `*Test`). 
    * Виконується на фазі `test`. 
    * Його задача — швидка перевірка коректності логіки окремих компонентів.
    * `maven-failsafe-plugin`: Відповідає за **інтеграційні тести** (класи `*IT`). 
    * Виконується на фазах `integration-test` та `verify`. 
    * Його задача — перевірка взаємодії компонентів системи.

2.  **`docker-maven-plugin`**:

    * **Призначення**: Декларативне управління Docker-контейнерами. 
    * Це усуває залежність від локально встановленого ПЗ та гарантує, що тести виконуються в ідентичному середовищі на будь-якій машині.
    * **Конфігурація**:
        * `<phase>pre-integration-test</phase>`: Перед запуском ІТ-тестів виконується `docker:start`, 
        * розгортаючи контейнер з **`postgres:15.5-alpine`**.
        * `<phase>post-integration-test</phase>`: Після завершення ІТ-тестів виконується `docker:stop`, 
        * зупиняючи та видаляючи контейнер, залишаючи систему чистою.
        * `<wait><log>...`: Maven не продовжить виконання, доки не знайде в логах контейнера рядок 
        * `database system is ready...`. 
        * Це запобігає "flaky tests" (нестабільним тестам), які падають через те, що база даних ще не готова приймати з'єднання.

**Найкраща практика**: Завжди відокремлюйте unit-тести від інтеграційних. 
Інтеграційні тести повинні виконуватися в повністю автоматизованому, ізольованому та відтворюваному середовищі.

-----

### **3: Базові класи для тестів**

Щоб уникнути дублювання коду, ми виносимо спільну конфігурацію в абстрактні базові класи.

* **`AbstractJpaTest`**:

    * **Призначення**: Спільна конфігурація для тестів, що взаємодіють з базою даних.
    * **`@SpringBootTest`**: Завантажує повний `ApplicationContext`, що необхідно для тестування з реальними бінами.
    * **`@ContextConfiguration(classes = {SchemaInit.class})`**: Явно додає нашу кастомну конфігурацію `SchemaInit` 
    * до тестового контексту.
    * **`@BeforeEach void setUp()`**: Перед кожним тестом очищує таблиці, гарантуючи ізоляцію тестів один від одного.

* **`BaseControllerIntegrationTest`**:

    * **Призначення**: Спільна конфігурація для інтеграційних тестів API.
    * **Схожий на `AbstractJpaTest`**: Успадковує всю логіку налаштування JPA та очищення БД.

-----

### **4: `SchemaInit.java` — Вирішення проблеми порядку ініціалізації**

**Проблема**: У чистому середовищі, як наш Docker-контейнер, база даних створюється порожньою, без будь-яких схем.

1.  Spring Boot запускається для тесту.
2.  Він бачить Liquibase і намагається виконати міграції.
3.  Liquibase з `application.yaml` знає, що має працювати зі схемою `public`.
4.  Він виконує `CREATE TABLE... IN SCHEMA public`.
5.  PostgreSQL відповідає помилкою: **`ERROR: schema "public" does not exist`**. Збірка падає.

**Рішення**: `SchemaInit` — це `@TestConfiguration`, яка виправляє порядок ініціалізації.

* **`InitializingBean`**: В методі `afterPropertiesSet()` виконується чистий JDBC-запит `CREATE SCHEMA IF NOT EXISTS...`. 
* Це відбувається після того, як `DataSource` бін створено, але до того, як Liquibase почне роботу.
* **`SpringLiquibaseDependsOnPostProcessor`**: Це `BeanFactoryPostProcessor`. 
* Він програмно модифікує визначення біна `SpringLiquibase`, додаючи йому залежність `depends-on="schemaInitBean"`. 
* Це примушує Spring Container гарантувати, що `schemaInitBean` буде повністю створений та ініціалізований 
* **перед** створенням біна `SpringLiquibase`.

-----

### **5: Концепція моків та Unit-тестування**

**Що таке "мок" (mock (макет) object)?**
**Мок** — це імітація реального об'єкта.

**Чому ми використовуємо моки?**

1.  **Ізоляція**: Щоб перевірити логіку **тільки** нашого класу (`FleetServiceImpl`), 
а не його залежностей (`MissionRepository`). 
Якщо тест падає, ми знаємо, що проблема саме в `FleetServiceImpl`.
2.  **Швидкість**: Моки працюють в пам'яті і виконуються за мікросекунди. 
Реальні звернення до БД — за мілісекунди, що в тисячі разів повільніше.
3.  **Контроль**: Ми можемо змусити мок імітувати будь-яку поведінку: успішне повернення даних, 
повернення `null` або `Optional.empty()`, або навіть викидання винятку. 
Це дозволяє тестувати всі, навіть найскладніші, сценарії обробки помилок.

-----

### **6: Unit-тестування на практиці**

**Файл**: `FleetServiceImplTest.java`

* **`@ExtendWith(MockitoExtension.class)`**: Активує фреймворк Mockito.
* **`@Mock private MissionRepository fleetRepository;`**: Створює мок-імітацію для `MissionRepository`.
* **`@InjectMocks private FleetServiceImpl fleetService;`**: Створює **реальний** екземпляр `FleetServiceImpl` і інжектимо в нього 
* всі об'єкти, позначені `@Mock`.

```java
@Test
void getMissionByIdShouldReturnMissionById() {
    // Given - Готуємо дані та "навчаємо" мок
    final Long missionId = 1L;
    final Mission mission = //... створюємо тестовий об'єкт
    
    // Кажемо моку: "Коли хтось викличе твій метод findById з missionId, 
    // поверни, будь ласка, цей підготовлений об'єкт mission"
    when(fleetRepository.findById(missionId))
            .thenReturn(java.util.Optional.of(mission));

    // When - Викликаємо метод, який тестуємо
    MissionDto missionDto = fleetService.getMissionById(missionId);

    // Then - Перевіряємо результат та взаємодії
    assertThat(missionDto).isNotNull(); // Перевіряємо, що результат не null
    verify(fleetRepository).findById(missionId); // Перевіряємо, що метод моку був викликаний
}
```

-----

### **7: Інтеграційні тести API та `@SpringBootTest`**

**Файл**: `FleetControllerIT.java`
**Мета**: Перевірити повний цикл від HTTP-запиту до відповіді, зачіпаючи всі шари додатку та реальну (тестову) БД.

Тут нам потрібен повний контекст додатку, тому ми використовуємо `@SpringBootTest`.

**Чому не `@DataJpaTest`?**

* `@DataJpaTest` — це анотація для **"зрізу" (slice) тестування**. 
* Вона завантажує **тільки** JPA-компоненти: ваші `@Entity`, `@Repository` та інфраструктуру для роботи з БД. 
* Вона **не** завантажує веб-шар: `@RestController`, `@Service`, `MockMvc` тощо.
* **`@SpringBootTest`**, навпаки, завантажує повний `ApplicationContext`, 
* роблячи всі ваші біни доступними для тесту. Це саме те, що потрібно для наскрізного тестування API.

-----

### **8: Тестування API за допомогою `MockMvc`**

`MockMvc` — це імітація веб-середовища Spring MVC. 
Вона дозволяє нам надсилати HTTP-запити до наших контролерів програмно, 
без реальної мережевої взаємодії.

* `@AutoConfigureMockMvc`: Автоматично налаштовує бін `MockMvc` в тестовому контексті.

<!-- end list -->

```java
@Test
@SneakyThrows
void getMissionsByStarship() {
    // Given - Створюємо та зберігаємо реальні дані в тестову БД
    final Starship starship = starshipRepository.save(creaStarship("Starship 1"));
    final Mission mission = missionRepository.save(createMission(starship, "Mission 1"));

    // When & Then
    mockMvc.perform(get("/starships/{starshipId}/missions", starship.getId())) // Виконуємо GET-запит
            .andExpect(status().isOk()) // Очікуємо HTTP-статус 200
            .andExpect(jsonPath("$[0].codename").value(mission.getCodename())); // Перевіряємо тіло JSON
}
```

* `jsonPath("$[0].codename")`: JSONPath — це мова запитів для JSON. Цей вираз означає: "взяти перший елемент (`[0]`) 
* кореневого масиву (`$`) і дістати значення його поля `codename`".

-----

### **9: Spring TestContext Framework та JUnit 5**

* **Що це?**: Фреймворк, що керує життєвим циклом `ApplicationContext` під час тестів. Вхідна точка — `@ExtendWith(SpringExtension.class)`.
* **Основні переваги**:
    1.  **Кешування контексту**: Якщо кілька тестових класів використовують ідентичну конфігурацію, 
  Spring завантажить контекст **лише один раз** і перевикористовуватиме його, значно прискорюючи виконання тестів.
    2.  **Dependency Injection**: Дозволяє вставляти біни з контексту прямо в тестові класи через `@Autowired`.
    3.  **Транзакційність**: Анотація `@Transactional` на тестовому методі автоматично відкочує транзакцію після його завершення, 
  забезпечуючи чистоту БД.

-----

### **10: Чистий API: Глобальна обробка винятків**

**Файл**: `GlobalExceptionHandler.java`

**Мета**: Централізовано перехоплювати винятки та перетворювати їх на структуровані HTTP-відповіді.

* **`@RestControllerAdvice`**: Глобальний обробник для всіх `@RestController`-ів.
* **`@ExceptionHandler(ResourceNotFoundException.class)`**: Метод-обробник, що спрацьовує **тільки** для `ResourceNotFoundException`.
* **`@ResponseStatus(HttpStatus.NOT_FOUND)`**: Встановлює HTTP-статус 404 для відповіді.
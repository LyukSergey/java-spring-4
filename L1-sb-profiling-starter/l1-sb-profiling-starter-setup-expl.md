### **Навіщо створювати власний стартер?**

Уявіть, що у вас є певна функціональність (наприклад, логування, метрики, або як у нашому випадку — 
профілювання часу виконання методів), яку ви хочете перевикористовувати у багатьох проєктах. 
Замість того, щоб копіювати класи та конфігурації з проєкту в проєкт, 
ви можете запакувати все це в єдиний **стартер**.

Інші розробники просто додадуть одну залежність у свій `pom.xml`, 
напишуть один рядок в `application.yml`, і магія станеться автоматично. 
Це і є філософія Spring Boot — **автоконфігурація** та **"домовились і забули"**.

-----

### **Архітектура нашого рішення**

Створюємо два проєкти:

1.  `L1-sb-profiling-starter`: Сам стартер, який містить всю логіку профілювання.
2.  `L1-sb-profiling-starter-test`: Простий веб-додаток, який буде використовувати наш стартер, щоб продемонструвати, як він працює.

-----

### **1: Створення самого стартера (`L1-sb-profiling-starter`)**

#### **1.1. Налаштування `pom.xml`**

Додаємо потрібні залежності.

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

* `spring-boot-autoconfigure`: Надає всі класи, необхідні для створення автоконфігурацій 
(наприклад, анотації `@Configuration`, `@Bean`, `@ConditionalOnProperty`).
* `spring-boot-configuration-processor`: Це інструмент, який під час компіляції сканує ваші класи, 
позначені `@ConfigurationProperties`, і генерує метадані (json). 
Завдяки цьому, коли хтось буде використовувати ваш стартер, 
IDE буде давати підказки для ваших кастомних властивостей в `application.yml`.

#### **1.2. Створення анотації `@Profiling`**

Ця анотація буде маркером, який вказуватиме, біни яких класів ми хочемо профілювати.

```java
package com.lss.l1sbprofilingstarter.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Profiling {}
```

* `@Target(ElementType.TYPE)`: Ця анотація вказує, що наш `@Profiling` можна застосовувати **лише до класів** (або інтерфейсів/енумів). Ми не можемо позначити нею метод чи поле.
    * **Аналогія:** Уявіть, що це табличка "Підлягає санітарній перевірці". Ви можете повісити її на двері цілого відділу (класу), але не на стіл окремого співробітника (методу).
* `@Retention(RetentionPolicy.RUNTIME)`: Це **ключова** анотація. 
Вона говорить компілятору, що інформацію про цю анотацію потрібно зберегти у скомпільованому `.class` 
файлі і зробити доступною під час виконання програми (runtime).
Саме завдяки цьому наш `BeanPostProcessor` зможе "побачити" цю анотацію на бінах, коли додаток вже буде запущено.

#### **1.3. Клас для налаштувань: `ProfilingProperties.java`**

Цей клас дозволить вмикати або вимикати наш функціонал через `application.yml`.

```java
package com.lss.l1sbprofilingstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "profiling")
public class ProfilingProperties {
    private boolean enabled = false; // За замовчуванням вимкнено
    // getters and setters
}
```

* `@ConfigurationProperties(prefix = "profiling")`: Ця анотація зв'язує поля цього класу з властивостями у файлі конфігурації. 
* Spring Boot автоматично знайде властивість `profiling.enabled` і запише її значення у поле `enabled`.

#### **1.4. Автоконфігурація: `ProfilingAutoConfiguration.java`**

Тут ми визначаємо, за яких умов і які біни створювати.

```java
package com.lss.l1sbprofilingstarter.config;

import com.lss.l1sbprofilingstarter.beanPostProcessor.ProfilingBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ProfilingProperties.class)
@ConditionalOnProperty(prefix = "profiling", name = "enabled", havingValue = "true")
public class ProfilingAutoConfiguration {

    @Bean
    public ProfilingBeanPostProcessor profilingBeanPostProcessor() {
        return new ProfilingBeanPostProcessor();
    }
}
```

* `@EnableConfigurationProperties(ProfilingProperties.class)`: Ця анотація "активує" наш клас `ProfilingProperties`. 
Вона реєструє його як бін і вмикає механізм зв'язування з `application.yml`.
* `@ConditionalOnProperty(...)`: Це "вимикач" для всієї нашої автоконфігурації. 
Вона каже Spring: "Створюй біни, визначені в цьому класі, **тільки за умови**, 
що в `application.yml` є властивість `profiling.enabled` і її значення дорівнює `true`". 
Якщо властивості немає або вона `false`, цей клас буде повністю проігноровано.

#### **1.5. Файл `org.springframework.boot.autoconfigure.AutoConfiguration.imports`**

Це найважливіший файл. Створіть його за шляхом `src/main/resources/META-INF/spring/`.

**Вміст файлу:**

```
com.lss.l1sbprofilingstarter.config.ProfilingAutoConfiguration
```

* **Навіщо він потрібен?** Під час запуску Spring Boot сканує classpath у пошуках файлів 
* `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 
у всіх JAR-залежностях. Він збирає з них список класів автоконфігурацій. 
**Без цього файлу Spring Boot ніколи не дізнається про існування вашого `ProfilingAutoConfiguration`**, 
і стартер просто не буде працювати. Це стандартний механізм реєстрації автоконфігурацій.

#### **1.6. Клас який додає нашу логіку до бінів: `ProfilingBeanPostProcessor.java`**

Це місце, де відбувається вся магія. 
`BeanPostProcessor` — це спеціальний інтерфейс Spring, який дозволяє вам втрутитися в процес створення **кожного** біна в додатку.

**Життєвий цикл біна та роботу `BeanPostProcessor`:**

1.  **1: Ініціалізація (`Instantiation`)**

    * Spring читає конфігурацію і бачить, що йому потрібно створити бін `myServiceImpl`. 
    * Він викликає його конструктор: `new MyServiceImpl()`.

2.  **2: Впровадження залежностей (`Populate properties`)**

    * Spring бачить, що `MyServiceImpl` потребує інші біни (наприклад, `UserRepository`), і впроваджує їх.

3.  **3: `postProcessBeforeInitialization`**

    * Spring передає **реальний, щойно створений** об'єкт `myServiceImpl` у метод `postProcessBeforeInitialization` 
    **кожного** `BeanPostProcessor`, включаючи наш.

    ```java
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Profiling.class)) {
            beansToProfile.put(beanName, bean.getClass());
        }
        return bean; // Повертаємо оригінальний, незмінений бін
    }
    ```

    * На цьому етапі ми **не змінюємо бін**. Ми лише перевіряємо, чи є на його класі наша анотація `@Profiling`. 
    Якщо так, ми **записуємо в мапу** — записуємо ім'я та клас біна в нашу мапу `beansToProfile`. 
    Це потрібно, щоб запам'ятати, з ким працювати на наступному етапі. 
    Ми повертаємо оригінальний бін, щоб він продовжив свій шлях по конвеєру.

4.  **4: Ініціалізація (`Initialization`)**

    * Spring викликає методи ініціалізації, наприклад, ті, що позначені `@PostConstruct`.

5.  **5: `postProcessAfterInitialization`**

    * Бін **повністю готовий**, але ще не доданий у "пул" готових бінів. 
    * Spring востаннє передає його в метод `postProcessAfterInitialization`.

    ```java
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beansToProfile.containsKey(beanName)) {
            Class<?> beanClass = beansToProfile.get(beanName);
            // Створюємо ПРОКСІ
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        long before = System.nanoTime();
                        Object result = method.invoke(bean, args); // Викликаємо оригінальний метод
                        long after = System.nanoTime();
                        System.out.println("Профілювання: метод " + method.getName() + " виконав свою роботу за " + (after - before) + " нс");
                        return result;
                    }
            );
        }
        return bean; // Повертаємо оригінальний бін, якщо він не потребує профілювання
    }
    ```

    * Ось тут і відбувається підміна. Ми перевіряємо нашу мапу. 
    * Якщо ім'я поточного біна є в мапі, то замість того, щоб повернути **оригінальний** об'єкт, 
    * ми створюємо і повертаємо його **проксі-двійника** за допомогою `Proxy.newProxyInstance()`.
    * **Що таке проксі?** Це об'єкт-обгортка, який виглядає точнісінько як оригінал 
    * (бо реалізує ті ж інтерфейси), але дозволяє нам перехоплювати **всі** виклики його методів.
    * Лямбда-вираз `(proxy, method, args) -> { ... }` — це логіка нашого проксі. 
    * Коли хтось в додатку викличе метод нашого біна, насправді він викличе цей код. Всередині нього ми:
        1.  Фіксуємо час до (`before`).
        2.  Викликаємо **справжній** метод на **справжньому** об'єкті: `method.invoke(bean, args)`.
        3.  Фіксуємо час після (`after`).
        4.  Друкуємо різницю.
        5.  Повертаємо результат виклику оригінального методу.

    В результаті Spring отримує від нас не оригінальний `MyServiceImpl`, а його проксі. 
І саме цей проксі-об'єкт він помістить у свій контекст і буде впроваджувати в інші біни, наприклад, у контролер.

-----

### **2: Використання стартера в тестовому проєкті (`L1-sb-profiling-starter-test`)**

#### **2.1. `pom.xml`**

Додаємо наш щойно створений стартер як залежність.

```xml
<dependency>
    <groupId>com.lss</groupId>
    <artifactId>L1-sb-profiling-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

#### **2.2. `application.yml`**

Вмикаємо наш функціонал.

```yaml
profiling:
  enabled: true
```

#### **2.3. Створення сервісу для тестування**

Створимо простий сервіс і позначимо його реалізацію нашою анотацією.

```java
// MyService.java - інтерфейс
public interface MyService {
    void doWork();
}

// MyServiceImpl.java - реалізація
@Service
@Profiling // Позначаємо, що цей бін потрібно профілювати
public class MyServiceImpl implements MyService {
    @Override
    public void doWork() {
        System.out.println("Виконую важливу роботу...");
        try {
            Thread.sleep(100); // Імітуємо роботу
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

#### **2.4. Контролер, що використовує сервіс**

```java
@RestController
@RequestMapping("/do")
@RequiredArgsConstructor
public class MyController {
    private final MyService myService; // Сюди Spring впровадить вже ПРОКСІ-ОБ'ЄКТ

    @GetMapping
    public ResponseEntity<Void> getUsers() {
        myService.doWork();
        return ResponseEntity.noContent().build();
    }
}
```

### **Результат**

Тепер, коли ви запустите `L1-sb-profiling-starter-test` і звернетесь до ендпоінту `http://localhost:8080/do`, 
в консолі ви побачите не тільки повідомлення "Виконую важливу роботу...", 
але й рядок, надрукований нашим проксі:

`Профілювання: метод doWork виконав свою роботу за 100... нс`

Це доводить, що наш `BeanPostProcessor` успішно спрацював і підмінив оригінальний бін на його проксі-версію, 
яка додала новий функціонал.
package testData;

import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.UserRepository;

//Інтерфейс CommandLineRunner — це спеціальний індикатор для Spring Boot.
//Коли ви створюєте бін (@Component), який реалізує цей інтерфейс,
// Spring Boot автоматично знаходить його і виконує його метод run()
// одразу після того, як додаток повністю запустився і контекст
// (ApplicationContext) готовий.

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
package com.example.fleet;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@ContextConfiguration(classes = {SchemaInit.class})
public abstract class AbstractJpaTest {

    @BeforeEach
    void setUp() {
        // Очистити таблиці перед кожним тестом
        // ...реалізація очищення...
    }
}

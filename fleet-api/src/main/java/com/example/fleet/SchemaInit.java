package com.example.fleet;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@TestConfiguration
public class SchemaInit {

    @Bean
    public InitializingBean schemaInitBean(DataSource dataSource) {
        return () -> {
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE SCHEMA IF NOT EXISTS public");
            }
        };
    }
}

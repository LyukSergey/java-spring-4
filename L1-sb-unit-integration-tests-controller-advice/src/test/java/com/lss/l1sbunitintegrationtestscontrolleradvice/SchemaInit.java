package com.lss.l1sbunitintegrationtestscontrolleradvice;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import liquibase.change.DatabaseChange;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@TestConfiguration
@ConditionalOnClass({ SpringLiquibase.class, DatabaseChange.class })
@ConditionalOnProperty(prefix = "spring.liquibase", name = "enabled", matchIfMissing = true)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@Import({SchemaInit.SpringLiquibaseDependsOnPostProcessor.class})
public class SchemaInit {
    @Component
    @ConditionalOnProperty(prefix = "spring.liquibase", name = "enabled", matchIfMissing = true)
    public static class SchemaInitBean implements InitializingBean {

        private final DataSource dataSource;
        private final String schemaName;

        @Autowired
        public SchemaInitBean(DataSource dataSource, @Value("${spring.liquibase.default-schema:public}") String schemaName) {
            this.dataSource = dataSource;
            this.schemaName = schemaName;
        }

        @Override
        public void afterPropertiesSet() {
            try (Connection conn = dataSource.getConnection();
                    Statement statement = conn.createStatement()) {
                statement.execute("create schema if not exists " + schemaName);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to create schema '" + schemaName + "'", e);
            }
        }
    }


    @ConditionalOnBean(SchemaInitBean.class)
    static class SpringLiquibaseDependsOnPostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {

        SpringLiquibaseDependsOnPostProcessor() {
            // Configure the 3rd party SpringLiquibase bean to depend on our SchemaInitBean
            super(SpringLiquibase.class, SchemaInitBean.class);
        }
    }
}

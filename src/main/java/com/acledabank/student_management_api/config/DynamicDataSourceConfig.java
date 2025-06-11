package com.acledabank.student_management_api.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Slf4j
public class DynamicDataSourceConfig {

    @Value("${app.db-type}")
    private String dbType;

    @Autowired
    private Environment env;

    @Bean
    @Primary
    public DataSource dataSource() {
        if ("postgres".equalsIgnoreCase(dbType)) {
            return DataSourceBuilder.create()
                    .driverClassName(env.getProperty("spring.datasource.postgres.driver-class-name"))
                    .url(env.getProperty("spring.datasource.postgres.url"))
                    .username(env.getProperty("spring.datasource.postgres.username"))
                    .password(env.getProperty("spring.datasource.postgres.password"))
                    .build();
        } else if ("oracle".equalsIgnoreCase(dbType)) {
            return DataSourceBuilder.create()
                    .driverClassName(env.getProperty("spring.datasource.oracle.driver-class-name"))
                    .url(env.getProperty("spring.datasource.oracle.url"))
                    .username(env.getProperty("spring.datasource.oracle.username"))
                    .password(env.getProperty("spring.datasource.oracle.password"))
                    .build();
        } else if ("sqlserver".equalsIgnoreCase(dbType)) {
            return DataSourceBuilder.create()
                    .driverClassName(env.getProperty("spring.datasource.sqlserver.driver-class-name"))
                    .url(env.getProperty("spring.datasource.sqlserver.url"))
                    .username(env.getProperty("spring.datasource.sqlserver.username"))
                    .password(env.getProperty("spring.datasource.sqlserver.password"))
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid app.db-type: " + dbType);
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.acledabank.student_management_api.model"); // your entity package

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // Set Hibernate properties dynamically
        Properties props = new Properties();

        if ("postgres".equalsIgnoreCase(dbType)) {
            props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            props.put("hibernate.jdbc.lob.non_contextual_creation", "true"); // Postgres workaround
        } else if ("oracle".equalsIgnoreCase(dbType)) {
            props.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        } else if ("sqlserver".equalsIgnoreCase(dbType)) {
            props.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        }

        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create-drop"); //create-drop or update
        em.setJpaProperties(props);

        return em;
    }
}


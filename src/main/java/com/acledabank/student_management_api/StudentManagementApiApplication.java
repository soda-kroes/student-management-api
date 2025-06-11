package com.acledabank.student_management_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class StudentManagementApiApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(StudentManagementApiApplication.class, args);
//	}

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StudentManagementApiApplication.class, args);
        String dbType = context.getEnvironment().getProperty("app.db-type");
        log.info("Application started with DB = {} ", dbType);
    }

}

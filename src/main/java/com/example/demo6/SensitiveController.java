// build.gradle
plugins {
    id 'org.springframework.boot' version '2.5.4'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

// src/main/java/com/example/demo/DemoApplication.java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

// src/main/java/com/example/demo/config/SensitiveConfig.java
package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensitiveConfig {

    @Value("${sensitive.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}

// src/main/java/com/example/demo/controller/SensitiveController.java
package com.example.demo.controller;

import com.example.demo.config.SensitiveConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SensitiveController {

    @Autowired
    private SensitiveConfig sensitiveConfig;

    @GetMapping("/api-key")
    public String getApiKey() {
        return sensitiveConfig.getApiKey();
    }
}

// src/main/resources/application.properties
sensitive.api.key=super-secret-api-key
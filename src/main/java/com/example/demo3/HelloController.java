// src/main/java/com/example/demo/HelloController.java
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        // Vulnerabilidad de XSS: el parámetro 'name' se incluye directamente en la respuesta HTML sin escape
        return "<html><body><h1>Hello, " + name + "!</h1></body></html>";
    }
}
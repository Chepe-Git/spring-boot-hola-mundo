// Java
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
public class CommandInjectionController {

    @GetMapping("/run")
    public String runCommand(@RequestParam String command) {
        StringBuilder output = new StringBuilder();
        try {
            // Vulnerabilidad de Inyección de Comandos
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (Exception e) {
            return "Error ejecutando el comando: " + e.getMessage();
        }
        return output.toString();
    }
}
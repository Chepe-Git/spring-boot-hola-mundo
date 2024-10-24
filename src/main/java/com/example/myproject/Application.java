// Application.java
package com.example.vulnerableapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// VulnerableController.java
@RestController
class VulnerableController {

    // Exposición de información sensible
    @GetMapping("/messages")
    public List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("Pruebas de seguridad");
        messages.add("Clave secreta: 1234"); // Información sensible expuesta
        return messages;
    }

    // Inyección SQL
    @GetMapping("/sqlInjection")
    public String sqlInjection(@RequestParam String username) {
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        String result = "Usuario no encontrado";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "user", "password");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                result = "Usuario encontrado: " + rs.getString("username");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "Error en la base de datos";
        }
        return result;
    }

    // Cross-Site Scripting (XSS)
    @GetMapping("/xss")
    public String xss(@RequestParam String input) {
        return "Mensaje recibido: " + input; // Sin sanitización
    }

    // Manejo inseguro de archivos
    @GetMapping("/readFile")
    public String readFile(@RequestParam String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al leer el archivo";
        }
    }

    // Cross-Site Request Forgery (CSRF)
    @PostMapping("/saveData")
    public String saveData(@RequestBody String data) {
        try {
            File file = new File("data.txt");
            FileWriter writer = new FileWriter(file, true);
            writer.write(data + "\n");
            writer.close();
            return "Datos guardados exitosamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al guardar datos";
        }
    }

    // Deserialización insegura
    @PostMapping("/deserialize")
    public String deserialize(@RequestBody String base64Object) {
        try {
            byte[] data = Base64.getDecoder().decode(base64Object);
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(data));
            Object obj = ois.readObject(); // Deserialización insegura
            ois.close();
            return "Objeto deserializado: " + obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error en la deserialización";
        }
    }
}

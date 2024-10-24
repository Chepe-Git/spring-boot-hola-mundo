package com.example.insecuredeserializationdemo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@RestController
public class DeserializationController {

    @PostMapping("/deserialize")
    public String deserialize(@RequestBody byte[] data) {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            Object obj = ois.readObject();
            return "Deserialized object: " + obj.toString();
        } catch (IOException | ClassNotFoundException e) {
            return "Deserialization failed: " + e.getMessage();
        }
    }
}
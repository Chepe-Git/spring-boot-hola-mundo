package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        // Ruta insegura: no se valida el nombre del archivo ni la ruta
        String uploadDir = "uploads/";
        File uploadFile = new File(uploadDir + file.getOriginalFilename());

        try {
            file.transferTo(uploadFile);
            return "File uploaded successfully: " + uploadFile.getAbsolutePath();
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
    }
}
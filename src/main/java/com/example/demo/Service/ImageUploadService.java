package com.example.demo.Service;

// package com.example.profile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageUploadService {

    @Value("${upload.dir}")
    private String uploadDir; // Directory where you want to save the uploaded files

    public String uploadImage(MultipartFile file, String email) throws IOException {
        // Create directory if it doesn't exist
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Define the file path based on the email or a unique identifier
        String fileName = email + "_profile_image_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Save the file to the directory
        file.transferTo(filePath);

        // Return the file URL (This could be a local URL or cloud storage URL)
        return "http://localhost:8080/images/" + fileName;
    }
}

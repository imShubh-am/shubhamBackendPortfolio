package com.shubham.controller;

import com.shubham.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping()
    public ResponseEntity<String> uploadFile(@RequestParam("resume") MultipartFile file) throws IOException {
        String uploadedFile = fileService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadedFile);
    }

    @GetMapping("/downloadResume")
    public ResponseEntity<?> downloadFile() throws DataFormatException, IOException {
        byte[] downloadFile = fileService.downloadFile();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(downloadFile);
    }
}

package com.praveen.portfolio.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class ResumeController {

    @GetMapping("/download-resume")
    public ResponseEntity<Resource> downloadResume() throws IOException {
        ClassPathResource pdfFile = new ClassPathResource("static/assets/files/Resume_Praveen.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Resume_Praveen.pdf\"")
                .body(new InputStreamResource(pdfFile.getInputStream()));
    }
}


package org.example.backend.controller;

import org.example.backend.model.assemblyai.FileUploadRequest;
import org.example.backend.service.AssemblyAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class BackendController {

    private final AssemblyAiService assemblyAiService;

    public BackendController(AssemblyAiService assemblyAiService) {
        this.assemblyAiService = assemblyAiService;
    }

    // File wird bei AssemblyAi hochgeladen
    @PostMapping("/upload")
    public Mono<ResponseEntity<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        // MultipartFile in Record umwandeln
        try {
            FileUploadRequest fileUploadRequest = new FileUploadRequest(
                    file.getOriginalFilename(),
                    file.getBytes()
            );
            return assemblyAiService.uploadFile(fileUploadRequest)
                    .map(response -> ResponseEntity.ok(response.upload_url()))  // Nur die URL extrahieren
                    .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Upload failed: " + e.getMessage())));
        } catch (IOException e) {
            return Mono.just(ResponseEntity.status(500).body("File processing failed: " + e.getMessage()));
        }
    }

}
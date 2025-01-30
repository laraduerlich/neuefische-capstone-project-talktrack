package org.example.backend.controller;

import org.example.backend.model.assemblyai.FileUploadRequest;
import org.example.backend.service.AssemblyAiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public Mono<ResponseEntity<String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // MultipartFile in Record umwandeln
            FileUploadRequest fileUploadRequest = new FileUploadRequest(
                    file.getOriginalFilename(),
                    file.getBytes()
            );
            return assemblyAiService.uploadFile(fileUploadRequest)
                    .map(response -> ResponseEntity.ok(response.upload_url()));  // Nur die URL extrahieren
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIOException(IOException e) {
        return e.getMessage();
    }


}
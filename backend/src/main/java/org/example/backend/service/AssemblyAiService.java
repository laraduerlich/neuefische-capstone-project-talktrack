package org.example.backend.service;

import org.example.backend.model.assemblyai.AssemblyAiResponse;
import org.springframework.core.io.ByteArrayResource;
import org.example.backend.model.assemblyai.FileUploadRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class AssemblyAiService {

    // API Key für AssemblyAI
    @Value("${assemblyai.api.key}")
    private String assemblyApiKey;

    private final WebClient webclient;

    public AssemblyAiService(WebClient.Builder builder) {
        this.webclient = builder.build();
    }

    public Mono<AssemblyAiResponse> uploadFile(FileUploadRequest fileUploadRequest) throws IOException {
            // FileUploadRequest in ByteArrayResource umwandeln
            ByteArrayResource fileResource = new ByteArrayResource(fileUploadRequest.content()) {
                @Override
                public String getFilename() {
                    return fileUploadRequest.fileName();  // Setze den Dateinamen aus dem Record
                }
            };

            // WebClient-Upload mit Multipart-Formular
            return webclient.post()
                    .uri("https://api.assemblyai.com/v2/upload")
                    .header(HttpHeaders.AUTHORIZATION, assemblyApiKey)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(fileResource)  // Datei als Body setzen
                    .retrieve()
                    .bodyToMono(AssemblyAiResponse.class);  // Antwort (Upload-URL) erhalten
    }
}

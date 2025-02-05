package org.example.backend.service;

import org.example.backend.model.assemblyai.AssemblyAiResponse;
import org.springframework.core.io.ByteArrayResource;
import org.example.backend.model.assemblyai.FileUploadRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class AssemblyAiUploadService {

    private final WebClient webclient;

    @Value("${app.assemblyai.api.key}")
    String assemblyApiKey;

    public AssemblyAiUploadService(@Value("${ASSEMBLY_URL}") String url, WebClient.Builder builder) {
        this.webclient = builder
                .baseUrl(url)
                .build();
    }

    // Upload the file on AssemblyAI
    public AssemblyAiResponse uploadFile(FileUploadRequest fileUploadRequest) throws NullPointerException {
        // transfer FileUploadRequest in ByteArrayResource
        ByteArrayResource fileResource = new ByteArrayResource(fileUploadRequest.content()) {
            @Override
            public String getFilename() {
                return fileUploadRequest.fileName();
            }
        };

        // WebClient-Upload with Multipart-Formular
        return webclient.post()
                .header(HttpHeaders.AUTHORIZATION, assemblyApiKey)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(fileResource)  // file in Body
                .retrieve()
                .toEntity(AssemblyAiResponse.class)// Response (Upload-URL)
                .block()
                .getBody();
    }
}

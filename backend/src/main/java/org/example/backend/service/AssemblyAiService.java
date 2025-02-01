package org.example.backend.service;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptOptionalParams;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import jakarta.annotation.PostConstruct;
import org.example.backend.model.assemblyai.AssemblyAiResponse;
import org.springframework.core.io.ByteArrayResource;
import org.example.backend.model.assemblyai.FileUploadRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Optional;

@Service
public class AssemblyAiService {

    // API Key for AssemblyAI
    @Value("${app.assemblyai.api.key}")
    private String assemblyApiKey;

    private final WebClient webclient;
    private AssemblyAI client;

    public AssemblyAiService(WebClient.Builder builder) {
        this.webclient = builder
                .baseUrl("https://api.assemblyai.com/v2")
                .build();
    }

    @PostConstruct // Runs after dependency injection to initialize the client
    public void initClient() {
        this.client = AssemblyAI.builder()
                .apiKey(assemblyApiKey)
                .build();
    }

    TranscriptOptionalParams params = TranscriptOptionalParams.builder()
            .speakerLabels(true)
            .build();

    // Upload the file on AssemblyAI
    public AssemblyAiResponse uploadFile(FileUploadRequest fileUploadRequest) throws IOException {
        // transfer FileUploadRequest in ByteArrayResource
        ByteArrayResource fileResource = new ByteArrayResource(fileUploadRequest.content()) {
            @Override
            public String getFilename() {
                return fileUploadRequest.fileName();
            }
        };

        // WebClient-Upload with Multipart-Formular
        return webclient.post()
                .uri("https://api.assemblyai.com/v2/upload")
                .header(HttpHeaders.AUTHORIZATION, assemblyApiKey)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(fileResource)  // file in Body
                .retrieve()
                .bodyToMono(AssemblyAiResponse.class) // Response (Upload-URL)
                .block();
    }

    // Transcribe the file on AssemblyAI
    public Optional<String> transcriptFile (AssemblyAiResponse response) {
        Transcript transcript = client.transcripts().transcribe(response.upload_url(), params);

        if (transcript.getStatus().equals(TranscriptStatus.ERROR)) {
            System.err.println(transcript.getError().get());
            return Optional.empty();
        }

        return transcript.getText();
    }

}

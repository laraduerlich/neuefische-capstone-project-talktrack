package org.example.backend.service;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptOptionalParams;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import jakarta.annotation.PostConstruct;
import org.example.backend.model.assemblyai.AssemblyAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssemblyAiTranscriptService {

    private AssemblyAI client;

    @Value("${app.assemblyai.api.key}")
    private String assemblyApiKey;

    private final TranscriptOptionalParams params = TranscriptOptionalParams.builder()
            .speakerLabels(true)
            .build();

    @PostConstruct // Runs after dependency injection to initialize the client
    public void initClient() {
        this.client = AssemblyAI.builder()
                .apiKey(assemblyApiKey)
                .build();
    }

    // Transcribe the file on AssemblyAI
    public Optional<String> transcriptFile (AssemblyAiResponse response) {
        Transcript transcript = client.transcripts().transcribe(response.upload_url(), params);

        if (transcript.getStatus().equals(TranscriptStatus.ERROR)) {
            transcript.getError().ifPresent(System.err::println);
            return Optional.empty();
        }

        return transcript.getText();
    }
}

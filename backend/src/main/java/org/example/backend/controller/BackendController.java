package org.example.backend.controller;

import org.example.backend.model.Summary;
import org.example.backend.model.assemblyai.AssemblyAiResponse;
import org.example.backend.model.assemblyai.FileUploadRequest;
import org.example.backend.service.AssemblyAiService;
import org.example.backend.service.SummaryService;
import org.example.backend.service.TranscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class BackendController {

    private final AssemblyAiService assemblyAiService;
    private final SummaryService service;
    private final TranscriptionService transcriptionService;

    public BackendController(AssemblyAiService assemblyAiService, SummaryService service, TranscriptionService transcriptionService) {
        this.assemblyAiService = assemblyAiService;
        this.service = service;
        this.transcriptionService = transcriptionService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
            // transfer MultipartFile in Record
            FileUploadRequest fileUploadRequest = new FileUploadRequest(
                    file.getOriginalFilename(),
                    file.getBytes()
            );
            // Upload the file on AssemblyAi
            AssemblyAiResponse assemblyAiResponse = assemblyAiService.uploadFile(fileUploadRequest);
            // transcribe the audio
            String transcript = transcriptionService.transcriptFile(assemblyAiResponse).orElse("Fehler");
            // summarize the transcript
        if (transcript.isBlank() || transcript.equals("") || transcript.equals("null") || transcript.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
            Summary summary = service.createSummary(transcript);

        return ResponseEntity.ok(summary.id());
    }

}
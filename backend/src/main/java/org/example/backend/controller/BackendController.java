package org.example.backend.controller;

import org.example.backend.model.Summary;
import org.example.backend.model.assemblyai.AssemblyAiResponse;
import org.example.backend.model.assemblyai.FileUploadRequest;
import org.example.backend.service.AssemblyAiUploadService;
import org.example.backend.service.AssemblyAiTranscriptService;
import org.example.backend.service.SummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BackendController {

    private final AssemblyAiUploadService assemblyAiUploadService;
    private final AssemblyAiTranscriptService transcriptService;
    private final SummaryService service;

    public BackendController(AssemblyAiUploadService assemblyAiUploadService, AssemblyAiTranscriptService transcriptService, SummaryService service) {
        this.assemblyAiUploadService = assemblyAiUploadService;
        this.transcriptService = transcriptService;
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("title") String title) throws IOException {
            // transfer MultipartFile in Record
            FileUploadRequest fileUploadRequest = new FileUploadRequest(
                    file.getOriginalFilename(),
                    file.getBytes()
            );
            // Upload the file on AssemblyAi
            AssemblyAiResponse assemblyAiResponse = assemblyAiUploadService.uploadFile(fileUploadRequest);
            // transcribe the audio
            String transcript = transcriptService.transcriptFile(assemblyAiResponse).orElse("Fehler");
            // summarize the transcript
            Summary summary = service.createSummary(transcript, title);

        return ResponseEntity.ok(summary.id());
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<Summary> getSummaryById(@PathVariable String id) {
        Summary summary = service.getSummaryById(id);
        if (summary != null) {
            return new ResponseEntity<>(summary, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/summaries")
    public ResponseEntity<List<Summary>> getAllSummaries() {
        List<Summary> summaries = service.getAllSummaries();
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @DeleteMapping("/summary/{id}")
    public ResponseEntity<String> deleteSummaryById(@PathVariable String id) {
        boolean isDeleted = service.deleteSummaryById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/summary/{id}")
    public ResponseEntity<String> updateSummaryById(@PathVariable String id, @RequestBody Summary summary) {
        Summary updateSummary = service.updateSummary(id, summary);
        if (updateSummary != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

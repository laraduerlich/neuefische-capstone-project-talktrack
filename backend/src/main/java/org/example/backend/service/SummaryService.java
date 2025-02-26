package org.example.backend.service;

import com.assemblyai.api.resources.transcripts.types.Transcript;
import org.example.backend.model.Summary;
import org.example.backend.repo.SummaryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryService {

    private final SummaryRepo repo;
    private final IdService idService;
    private final ChatGPTService chatGPTService;


    public SummaryService(SummaryRepo repo, IdService idService, ChatGPTService chatGPTService) {
        this.repo = repo;
        this.idService = idService;
        this.chatGPTService = chatGPTService;
    }

    public Summary createSummary(String transcript, String title) {
        Summary newSummary = new Summary (
                idService.generateId(),
                title,
                chatGPTService.summarizeTranscript(transcript)
        );
        return repo.save(newSummary);
    }

    public Summary getSummaryById(String id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Summary could not be found"));
    }

    public List<Summary> getAllSummaries() {
        return repo.findAll();
    }

    public Boolean deleteSummaryById(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Summary could not be deleted");
        }
    }

    public Summary updateSummary(String id, Summary summary) {
        if (repo.existsById(id)) {
            return repo.save(summary);
        } else {
            throw new RuntimeException("Summary could not be updated");
        }
    }
}

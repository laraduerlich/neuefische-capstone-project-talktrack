package org.example.backend.service;

import org.example.backend.model.Summary;
import org.example.backend.repo.SummaryRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SummaryServiceTest {

    private final SummaryRepo repo = mock(SummaryRepo.class);
    private final IdService idService = mock(IdService.class);
    private final ChatGPTService chatGPTService = mock(ChatGPTService.class);

    @Test
    void createSummary_shouldCreateSummary_whenCalled() {
        // GIVEN
        SummaryService service = new SummaryService(repo, idService, chatGPTService);
        String transcript = "Hallo";

        Summary expected = new Summary("1", "title", "Hallo");
        when(idService.generateId()).thenReturn("1");
        when(chatGPTService.summarizeTranscript(transcript)).thenReturn("Hallo");
        when(repo.save(expected)).thenReturn(expected);

        // WHEN
        Summary actual = service.createSummary(transcript);

        // THEN
        assertEquals(expected,actual);
        verify(repo).save(expected);
    }


}
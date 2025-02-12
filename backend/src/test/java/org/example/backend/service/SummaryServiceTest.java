package org.example.backend.service;

import org.example.backend.model.Summary;
import org.example.backend.repo.SummaryRepo;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SummaryServiceTest {

    private final SummaryRepo repo = mock(SummaryRepo.class);
    private final IdService idService = mock(IdService.class);
    private final ChatGPTService chatGPTService = mock(ChatGPTService.class);

    // --------------------------------------- CREATE ---------------------------------------
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
        Summary actual = service.createSummary(transcript, expected.title());

        // THEN
        assertEquals(expected,actual);
        verify(repo).save(expected);
    }

    // --------------------------------------- GET BY ID ---------------------------------------
    @Test
    void getSummaryById_shouldReturnSummary_whenCalledWithValidId() {
        // GIVEN
        SummaryService service = new SummaryService(repo, idService, chatGPTService);
        Summary summary = new Summary("1", "Test", "Test");
        when(repo.findById("1")).thenReturn(Optional.of(summary));
        when(idService.generateId()).thenReturn("1");

        // WHEN
        Summary actual = service.getSummaryById("1");

        // THEN
        assertEquals(summary, actual);
    }

    @Test
    void getSummaryById_shouldThrowException_whenCalledWithInvalidId() {
        // GIVEN
        SummaryService service = new SummaryService(repo, idService, chatGPTService);

        // WHEN & THEN
        try {
            service.getSummaryById("1");
            fail("An exception is expected, but none is thrown!");
        } catch (Exception e) {
            assertEquals("Summary could not be found", e.getMessage());
        }
    }

    // --------------------------------------- GET ALL ---------------------------------------
    @Test
    void getAllSummaries_shouldReturnEmptyList_whenCalledInitially() {
        // GIVEN
        SummaryService service = new SummaryService(repo, idService, chatGPTService);
        List<Summary> expected = Collections.emptyList();

        // WHEN
        List<Summary> actual = service.getAllSummaries();

        // THEN
        assertEquals(expected, actual);
    }

    // --------------------------------------- DELETE ---------------------------------------
    @Test
    void deleteSummaryById_shouldDeleteSummary_whenCalledWithValidId() {
        // GIVEN
        SummaryService service = new SummaryService(repo, idService, chatGPTService);
        Summary summary = new Summary("1", "title", "Hallo");
        when(repo.existsById(summary.id())).thenReturn(true);

        // WHEN
        service.deleteSummaryById(summary.id());

        // THEN
        verify(repo).deleteById(summary.id());
    }

    @Test
    void deleteSummaryById_shouldThrowException_whenCalledWithInvalidId() {
        // GIVEN
        SummaryService service = new SummaryService(repo, idService, chatGPTService);
        when(repo.existsById(anyString())).thenReturn(false);

        // WHEN & THEN
        try {
            service.deleteSummaryById("1");
            fail("An exception is expected, but none is thrown!");
        } catch (Exception e) {
            assertEquals("Summary could not be deleted", e.getMessage());
        }
    }

    // --------------------------------------- UPDATE ---------------------------------------
    @Test
    void updateSummary_shouldUpdateSummary_whenCalledWithValidId() {
        // GIVEN
        SummaryService service = new SummaryService(repo, idService, chatGPTService);
        Summary summary = new Summary("1", "title", "Hallo");
        when(repo.existsById("1")).thenReturn(true);
        when(repo.save(summary)).thenReturn(summary);

        // WHEN
        Summary actual = service.updateSummary(summary.id(), summary);

        // THEN
        assertEquals(summary, actual);
        verify(repo).save(summary);
    }

    @Test
    void updateSummary_shouldThrowException_whenCalledWithInvalidId() {
        // GIVEN
        SummaryService service = new SummaryService(repo, idService, chatGPTService);
        Summary summary = new Summary("1", "title", "Hallo");
        when(repo.existsById(anyString())).thenReturn(false);

        // WHEN & THEN
        try {
            service.updateSummary(summary.id(), summary);
            fail("An exception is expected, but none is thrown!");
        } catch (Exception e) {
            assertEquals("Summary could not be updated", e.getMessage());
        }
    }
}

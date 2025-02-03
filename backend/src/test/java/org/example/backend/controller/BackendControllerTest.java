package org.example.backend.controller;

import org.example.backend.model.Summary;
import org.example.backend.model.assemblyai.AssemblyAiResponse;
import org.example.backend.model.assemblyai.FileUploadRequest;
import org.example.backend.service.AssemblyAiService;
import org.example.backend.service.SummaryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class BackendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BackendController backendController;

    @MockBean
    private AssemblyAiService assemblyAiService;

    @MockBean
    private SummaryService service;

    @DynamicPropertySource
    static void registerAssemblyAiProperties(DynamicPropertyRegistry registry) {
        registry.add("app.assemblyai.api.key", () -> "test-assembly");
    }

    @Test
    void uploadFile_checkResponseStatus() throws Exception {
        // GIVEN
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",               // Name des Dateifeldes
                "testfile.m4a",             // Dateiname
                "audio/m4a",                // MIME-Typ
                "This is a test file".getBytes()  // Dateiinhalt als Byte-Array
        );

        AssemblyAiResponse mockResponse = new AssemblyAiResponse("mock-file-id");
        Summary mockSummary = new Summary("1","title", "Kurzfassung");

        when(assemblyAiService.uploadFile(any(FileUploadRequest.class))).thenReturn(mockResponse);
        when(assemblyAiService.transcriptFile(mockResponse)).thenReturn(Optional.of("Test Transkript"));
        when(service.createSummary("Test Transkript")).thenReturn(mockSummary);

        // WHEN & THEN
        mockMvc.perform(multipart("/api/upload")
                .file(mockFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    void uploadFile_shouldThrowException () throws Exception {
        // Mock MultipartFile GIVEN
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.txt");
        when(mockFile.getBytes()).thenThrow(new IOException());

        // WHEN & THEN
        assertThrows(IOException.class, () -> backendController.uploadFile(mockFile));
    }
}
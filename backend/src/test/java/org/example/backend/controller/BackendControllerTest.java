package org.example.backend.controller;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.backend.model.Summary;
import org.example.backend.model.assemblyai.AssemblyAiResponse;
import org.example.backend.model.assemblyai.FileUploadRequest;
import org.example.backend.service.AssemblyAiService;
import org.example.backend.service.SummaryService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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

    @Mock
    AssemblyAiService assemblyAiService;

    private static MockWebServer assemblyAiMockServer;

    private static MockWebServer chatGPTMockServer;

    @BeforeAll
    static void setup() throws IOException {
        assemblyAiMockServer = new MockWebServer();
        chatGPTMockServer = new MockWebServer();
        assemblyAiMockServer.start();
        chatGPTMockServer.start();
    }

    @AfterAll
    static void shutDown() throws IOException {
        assemblyAiMockServer.shutdown();
        chatGPTMockServer.shutdown();
    }

    @DynamicPropertySource
    static void backendPropsAssemblyAi(DynamicPropertyRegistry registry){
        registry.add( "urlAssembly", () -> assemblyAiMockServer.url("/").toString());
        registry.add( "chatGPTApiKey", () -> chatGPTMockServer.url("/").toString());
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
        AssemblyAiResponse mockResponse = new AssemblyAiResponse("test-url");
        String transcript = "Das ist ein Test";

        assemblyAiMockServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(String.valueOf(mockResponse)));

        chatGPTMockServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody("""
                        {
                         "id": "chatcmpl-AlYfXsC7MleTcISu5Tih3xeaJfKdL",
                         "object": "chat.completion",
                         "created": 1735898047,
                         "model": "gpt-3.5-turbo-0125",
                         "choices": [
                             {
                                   "index": 0,
                                   "message": {
                                        "role": "user",
                                        "content": "Das ist eine Zusammenfassung",
                                        "refusal": null
                                   },
                                   "logprobs": null,
                                   "finish_reason": "stop"
                             }
                             ]
                        }
                        """));

        when(assemblyAiService.transcriptFile(mockResponse)).thenReturn(Optional.of(transcript));

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/upload")
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
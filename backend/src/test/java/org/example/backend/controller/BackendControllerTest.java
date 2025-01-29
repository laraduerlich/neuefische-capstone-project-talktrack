package org.example.backend.controller;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureMockWebServiceServer;
import org.springframework.boot.test.autoconfigure.webservices.client.MockWebServiceServerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BackendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void registerAssemblyAiProperties(DynamicPropertyRegistry registry) {
        registry.add("assemblyai.api.key", () -> "test-assembly");
    }

    @Test
    void uploadFile_checkResponseStatus() throws Exception {
        // GIVEN
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                     // Name des Dateifeldes
                "testfile.m4a",             // Dateiname
                "audio/m4a",               // MIME-Typ
                "This is a test file".getBytes()  // Dateiinhalt als Byte-Array
        );

        // WHEN & THEN
        mockMvc.perform(multipart("/api/upload")
                .file(mockFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

}
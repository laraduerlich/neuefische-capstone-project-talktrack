package org.example.backend.service;

import org.example.backend.model.chatgpt.ChatGPTMessage;
import org.example.backend.model.chatgpt.ChatGPTRequest;
import org.example.backend.model.chatgpt.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ChatGPTService {

    private final RestClient restClient;

    // Instruction for ChatGPT
    private final String instruction = "Fasse Transkript zusammen und arbeite die westenlichen Punkte raus:";

    // API Key for ChatGPT
    @Value("${app.chatgpt.api.key}")
    private String apiKey;

    public ChatGPTService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public String summarizeTranscript(String transcript) {
        ChatGPTResponse response = restClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ChatGPTRequest(
                        "gpt-3.5-turbo",
                        List.of(new ChatGPTMessage("user",instruction  + transcript))
                ))
                .retrieve()
                .body(ChatGPTResponse.class);

        return response.choices().get(0).message().content();
    }
}

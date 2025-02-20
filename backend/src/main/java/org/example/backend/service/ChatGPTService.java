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
    private final String instruction = "Fasse das folgende Transkript zusammen. " +
            "Teile es in thematische Abschnitte und fasse die wichtigsten Aussagen jedes Abschnitts in Unterpunkten vollständig zusammen, " +
            "ohne wichtige Informationen zu kürzen oder zu weglassen. Achte darauf, dass alle relevanten Punkte erhalten bleiben. " +
            "Hier ist das Transkript:";

    // API Key for ChatGPT
    @Value("${app.chatgpt.api.key}")
    private String chatGPTApiKey;

    public ChatGPTService(@Value("${CHATGPT_URL}") String url, RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(url)
                .build();
    }

    public String summarizeTranscript(String transcript) {
        ChatGPTResponse response = restClient.post()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + chatGPTApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ChatGPTRequest(
                        "gpt-3.5-turbo",
                        List.of(new ChatGPTMessage("user",instruction  + transcript))
                ))
                .retrieve()
                .body(ChatGPTResponse.class);

        // Check if the response is valid
        if (response == null || response.choices() == null || response.choices().isEmpty()) {
            throw new RuntimeException("Fehler: OpenAI API hat keine gültige Antwort zurückgegeben!");
        }

        return response.choices().get(0).message().content();
    }
}

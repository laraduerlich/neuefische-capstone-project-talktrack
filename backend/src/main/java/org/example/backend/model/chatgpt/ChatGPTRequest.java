package org.example.backend.model.chatgpt;

import java.util.List;

public record ChatGPTRequest(String model,
                             List<ChatGPTMessage> messages) {
}

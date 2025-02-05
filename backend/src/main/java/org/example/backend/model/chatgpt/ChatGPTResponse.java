package org.example.backend.model.chatgpt;

import java.util.List;

public record ChatGPTResponse(List<ChatGPTChoice> choices) {
}

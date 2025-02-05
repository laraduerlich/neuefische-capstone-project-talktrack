package org.example.backend.model.chatgpt;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class ChatGPTModelTest {

    // ------------------------ ChatGPTChoice ------------------------
    @Test
    void Choice_shouldCreateCorrectly() {
        // GIVEN
        ChatGPTMessage message = new ChatGPTMessage("user", "Hello, how are you?");

        // WHEN
        ChatGPTChoice choice = new ChatGPTChoice(message);

        // THEN
        assertThat(choice.message()).isNotNull();
        assertThat(choice.message().role()).isEqualTo("user");
        assertThat(choice.message().content()).isEqualTo("Hello, how are you?");
    }

    @Test
    void Choice_shouldHandleNullMessage() {
        // WHEN
        ChatGPTChoice choice = new ChatGPTChoice(null);

        // THEN
        assertThat(choice.message()).isNull();
    }

    // ------------------------ ChatGPTMessage ------------------------

    @Test
    void Message_shouldCreateMessageWithRoleAndContent() {
        // GIVEN
        String role = "user";
        String content = "Test content";

        // WHEN
        ChatGPTMessage message = new ChatGPTMessage(role, content);

        // THEN
        assertEquals(role, message.role());
        assertEquals(content, message.content());
    }

    @Test
    void Message_shouldHandleNullValues() {
        // WHEN
        ChatGPTMessage message = new ChatGPTMessage(null, null);

        // THEN
        assertNull(message.role());
        assertNull(message.content());
    }

    // ------------------------ ChatGPTRequest ------------------------

    @Test
    void Request_shouldCreateRequestWithModelAndMessages() {
        // GIVEN
        String model = "gpt-4";
        List<ChatGPTMessage> messages = List.of(new ChatGPTMessage("user", "Hello"));

        // WHEN
        ChatGPTRequest request = new ChatGPTRequest(model, messages);

        // THEN
        assertEquals(model, request.model());
        assertEquals(messages, request.messages());
    }

    @Test
    void Request_shouldHandleNullValues() {
        // WHEN
        ChatGPTRequest request = new ChatGPTRequest(null, null);

        // THEN
        assertNull(request.model());
        assertNull(request.messages());
    }

    // ------------------------ ChatGPTResponse ------------------------

    @Test
    void Response_shouldCreateResponseWithChoices() {
        // GIVEN
        List<ChatGPTChoice> choices = List.of(new ChatGPTChoice(new ChatGPTMessage("user","Test")));

        // WHEN
        ChatGPTResponse response = new ChatGPTResponse(choices);

        // THEN
        assertEquals(choices, response.choices());
    }

    @Test
    void Response_shouldHandleNullValues() {
        // WHEN
        ChatGPTResponse response = new ChatGPTResponse(null);

        // THEN
        assertNull(response.choices());
    }

}


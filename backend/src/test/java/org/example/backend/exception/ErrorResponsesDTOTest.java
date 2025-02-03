package org.example.backend.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponsesDTOTest {

    @Test
    void shouldStoreMessageCorrectly() {
        // GIVEN
        String expectedMessage = "Test error message";

        // WHEN
        ErrorResponsesDTO errorResponse = new ErrorResponsesDTO(expectedMessage);

        // THEN
        assertThat(errorResponse.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldHandleNullMessage() {
        // WHEN
        ErrorResponsesDTO errorResponse = new ErrorResponsesDTO(null);

        // THEN
        assertThat(errorResponse.getMessage()).isNull();
    }

}
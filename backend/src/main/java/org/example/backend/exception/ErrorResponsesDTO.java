package org.example.backend.exception;

public class ErrorResponsesDTO {

    private final String message;

    public ErrorResponsesDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

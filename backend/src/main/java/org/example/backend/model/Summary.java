package org.example.backend.model;

import java.util.UUID;

public record Summary(
        UUID id,
        String title,
        String link,
        String transcription
) {}

package org.example.backend.model;

import java.util.UUID;

public record Summary(
        String id,
        String title,
        String text
) {}


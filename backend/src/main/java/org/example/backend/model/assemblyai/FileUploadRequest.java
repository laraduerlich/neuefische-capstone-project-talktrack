package org.example.backend.model.assemblyai;

public record FileUploadRequest(String fileName, byte[] content) { }

package org.example.backend.model.assemblyai;

import java.util.Arrays;

public record FileUploadRequest(String fileName, byte[] content) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FileUploadRequest that = (FileUploadRequest) obj;
        return fileName.equals(that.fileName) && Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return 31 * fileName.hashCode() + Arrays.hashCode(content);
    }

    @Override
    public String toString() {
        return "FileUploadRequest{" +
                "fileName='" + fileName + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}

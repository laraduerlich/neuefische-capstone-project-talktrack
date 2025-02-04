package org.example.backend.model.assemblyai;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileUploadRequestTest {

    @Test
    void testEquals_SameContent_ShouldBeEqual() {
        FileUploadRequest req1 = new FileUploadRequest("test.txt", new byte[]{1, 2, 3});
        FileUploadRequest req2 = new FileUploadRequest("test.txt", new byte[]{1, 2, 3});

        assertEquals(req1, req2);
    }

    @Test
    void testEquals_DifferentContent_ShouldNotBeEqual() {
        FileUploadRequest req1 = new FileUploadRequest("test.txt", new byte[]{1, 2, 3});
        FileUploadRequest req2 = new FileUploadRequest("test.txt", new byte[]{4, 5, 6});

        assertNotEquals(req1, req2);
    }

    @Test
    void testHashCode_SameContent_ShouldHaveSameHashCode() {
        FileUploadRequest req1 = new FileUploadRequest("test.txt", new byte[]{1, 2, 3});
        FileUploadRequest req2 = new FileUploadRequest("test.txt", new byte[]{1, 2, 3});

        assertEquals(req1.hashCode(), req2.hashCode());
    }

    @Test
    void testHashCode_DifferentContent_ShouldHaveDifferentHashCode() {
        FileUploadRequest req1 = new FileUploadRequest("test.txt", new byte[]{1, 2, 3});
        FileUploadRequest req2 = new FileUploadRequest("test.txt", new byte[]{4, 5, 6});

        assertNotEquals(req1.hashCode(), req2.hashCode());
    }

    @Test
    void testToString_ShouldReturnCorrectString() {
        FileUploadRequest req = new FileUploadRequest("test.txt", new byte[]{1, 2, 3});

        String expected = "FileUploadRequest{fileName='test.txt', content=" + Arrays.toString(new byte[]{1, 2, 3}) + "}";
        assertEquals(expected, req.toString());
    }

}
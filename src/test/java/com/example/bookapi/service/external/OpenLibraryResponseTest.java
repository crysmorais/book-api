package com.example.bookapi.service.external;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OpenLibraryResponseTest {

    @Test
    void testSetAndGetDocs() {
        // Arrange
        OpenLibraryDoc doc1 = new OpenLibraryDoc();
        doc1.setTitle("Title 1");
        doc1.setKey("/works/OL123W");

        OpenLibraryDoc doc2 = new OpenLibraryDoc();
        doc2.setTitle("Title 2");
        doc2.setKey("/works/OL456W");

        OpenLibraryResponse response = new OpenLibraryResponse();
        response.setDocs(List.of(doc1, doc2));

        // Act
        List<OpenLibraryDoc> result = response.getDocs();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).getTitle());
        assertEquals("Title 2", result.get(1).getTitle());
        assertEquals("/works/OL456W", result.get(1).getKey());
    }
}
package com.example.bookapi.service.external;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class OpenLibraryDocTest {

    @Test
    public void testGettersAndSetters() {
        OpenLibraryDoc doc = new OpenLibraryDoc();
        doc.setTitle("Duna");
        doc.setAuthorName(Arrays.asList("Frank Herbert"));
        doc.setSubject(Arrays.asList("Sci-Fi", "Ficção"));
        doc.setKey("/works/OL123W");

        assertEquals("Duna", doc.getTitle());
        assertEquals("Frank Herbert", doc.getAuthorName().get(0));
        assertEquals("Sci-Fi", doc.getSubject().get(0));
        assertEquals("/works/OL123W", doc.getKey());
    }
}
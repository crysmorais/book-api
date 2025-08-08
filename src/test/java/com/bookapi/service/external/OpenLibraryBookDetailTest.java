package com.bookapi.service.external;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OpenLibraryBookDetailTest {

    @Test
    public void testSetAndGetTitle() {
        OpenLibraryBookDetail detail = new OpenLibraryBookDetail();
        detail.setTitle("1984");
        assertEquals("1984", detail.getTitle());
    }

    @Test
    public void testSetAndGetByStatement() {
        OpenLibraryBookDetail detail = new OpenLibraryBookDetail();
        detail.setBy_statement("George Orwell");
        assertEquals("George Orwell", detail.getBy_statement());
    }
}
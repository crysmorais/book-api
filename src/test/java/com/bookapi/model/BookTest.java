package com.bookapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testDefaultConstructorAndSetters() {
        Book book = new Book();

        book.setId(1L);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setGenre("Ficção");
        book.setExternalId("EXT123");
        book.setOpenLibraryId("OL123");
        book.setPublishedDate("1949");

        assertEquals(1L, book.getId());
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
        assertEquals("Ficção", book.getGenre());
        assertEquals("EXT123", book.getExternalId());
        assertEquals("OL123", book.getOpenLibraryId());
        assertEquals("1949", book.getPublishedDate());
    }

    @Test
    void testPartialConstructor() {
        Book book = new Book("Dom Casmurro", "Machado de Assis", "Romance", "OL456");

        assertNull(book.getId());
        assertEquals("Dom Casmurro", book.getTitle());
        assertEquals("Machado de Assis", book.getAuthor());
        assertEquals("Romance", book.getGenre());
        assertEquals("OL456", book.getOpenLibraryId());
        assertNull(book.getExternalId());
        assertNull(book.getPublishedDate());
    }

    @Test
    void testFullConstructor() {
        Book book = new Book(
                10L,
                "O Hobbit",
                "J.R.R. Tolkien",
                "Fantasia",
                "EXT999",
                "OL789",
                "1937"
        );

        assertEquals(10L, book.getId());
        assertEquals("O Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals("Fantasia", book.getGenre());
        assertEquals("EXT999", book.getExternalId());
        assertEquals("OL789", book.getOpenLibraryId());
        assertEquals("1937", book.getPublishedDate());
    }
}
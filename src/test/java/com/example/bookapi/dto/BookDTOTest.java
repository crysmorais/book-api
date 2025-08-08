
package com.example.bookapi.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookDTOTest {

    @Test
    public void testGettersAndSetters() {
        BookDTO book = new BookDTO();
        book.setId(1L);
        book.setTitle("Dom Casmurro");
        book.setAuthor("Machado de Assis");
        book.setGenre("Romance");
        book.setExternalId("OL123456M");

        assertEquals(1L, book.getId());
        assertEquals("Dom Casmurro", book.getTitle());
        assertEquals("Machado de Assis", book.getAuthor());
        assertEquals("Romance", book.getGenre());
        assertEquals("OL123456M", book.getExternalId());
    }

    @Test
    public void testConstructor() {
        BookDTO book = new BookDTO(2L, "1984", "George Orwell", "Ficção", "OL654321M");

        assertEquals(2L, book.getId());
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
        assertEquals("Ficção", book.getGenre());
        assertEquals("OL654321M", book.getExternalId());
    }
}

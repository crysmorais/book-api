
package com.bookapi.service.external;

import com.bookapi.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookExternalServiceTest {

    @Test
    void testFetchBooksByAuthor_ReturnsBooks() {
        BookExternalService service = new BookExternalService();
        List<Book> books = service.fetchBooksByAuthor("Agatha");

        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertTrue(books.stream().anyMatch(b -> b.getAuthor().toLowerCase().contains("agatha")));
    }

    @Test
    void testFetchBookById_ReturnsBook() {
        BookExternalService service = new BookExternalService();
        var result = service.fetchBookById(1L);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertNotNull(result.get().getTitle());
    }
}

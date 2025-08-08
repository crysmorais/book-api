package com.example.bookapi.service;

import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.external.BookExternalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository bookRepository;
    private BookExternalService bookExternalService;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookExternalService = mock(BookExternalService.class);
        bookService = new BookService(bookRepository, bookExternalService);
    }

    @Test
    void testGetAllBooksFromDatabase() {
        Book book = new Book(1L, "Title", "Author", "Genre", "Ext123", "OL1M", "2025-08-01");
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookDTO> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
        verify(bookRepository, never()).saveAll(any());
    }

    @Test
    void testGetAllBooksFromExternalAPI() {
        when(bookRepository.findAll()).thenReturn(List.of());
        Book externalBook = new Book(1L, "External Title", "External Author", "External Genre", "Ext999", "OL9M", "2025-08-01");
        when(bookExternalService.fetchAllBooks()).thenReturn(List.of(externalBook));

        List<BookDTO> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        verify(bookRepository).saveAll(List.of(externalBook));
    }

    @Test
    void testGetBookByIdFromDatabase() {
        Book book = new Book(1L, "Title", "Author", "Genre", "Ext123", "OL1M", "2025-08-01");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("Title", result.getTitle());
    }

    @Test
    void testGetBookByIdFromExternalAPI() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());
        Book externalBook = new Book(2L, "API Title", "API Author", "API Genre", "ExtAPI", "OL2M", "2025-08-01");
        when(bookExternalService.fetchBookById(2L)).thenReturn(Optional.of(externalBook));

        BookDTO result = bookService.getBookById(2L);

        assertNotNull(result);
        verify(bookRepository).save(externalBook);
    }

    @Test
    void testGetBooksByAuthorFromDatabase() {
        Book book = new Book(3L, "Author Book", "Agatha", "Mystery", "ExtAgatha", "OL3M", "2025-08-01");
        when(bookRepository.findByAuthorContainingIgnoreCase("Agatha")).thenReturn(List.of(book));

        List<BookDTO> result = bookService.getBooksByAuthor("Agatha");

        assertEquals(1, result.size());
        assertEquals("Agatha", result.get(0).getAuthor());
    }

    @Test
    void testGetBooksByGenreFromAPIWhenNotInDatabase() {
        when(bookRepository.findByGenreContainingIgnoreCase("Sci-Fi")).thenReturn(List.of());
        Book externalBook = new Book(4L, "Space Odyssey", "Clarke", "Sci-Fi", "Ext999", "OL4M", "2025-08-01");
        when(bookExternalService.fetchBooksByGenre("Sci-Fi")).thenReturn(List.of(externalBook));

        List<BookDTO> result = bookService.getBooksByGenre("Sci-Fi");

        assertEquals(1, result.size());
        assertEquals("Sci-Fi", result.get(0).getGenre());
        verify(bookRepository).saveAll(List.of(externalBook));
    }
}

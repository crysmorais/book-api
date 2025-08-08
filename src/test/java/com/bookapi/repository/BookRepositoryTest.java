package com.bookapi.repository;

import com.bookapi.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Deve salvar e buscar livro por autor exato (ignorando case)")
    void testFindByAuthorIgnoreCase() {
        Book book = new Book("Título A", "Autor Teste", "Ficção", "OL123");
        bookRepository.save(book);

        List<Book> result = bookRepository.findByAuthorIgnoreCase("autor teste");
        assertEquals(1, result.size());
        assertEquals("Autor Teste", result.get(0).getAuthor());
    }

    @Test
    @DisplayName("Deve salvar e buscar livro por autor parcialmente (ignorando case)")
    void testFindByAuthorContainingIgnoreCase() {
        bookRepository.save(new Book("Título B", "João Silva", "Drama", "OL456"));

        List<Book> result = bookRepository.findByAuthorContainingIgnoreCase("joão");
        assertFalse(result.isEmpty());
        assertEquals("João Silva", result.get(0).getAuthor());
    }

    @Test
    @DisplayName("Deve salvar e buscar livro por gênero exato (ignorando case)")
    void testFindByGenreIgnoreCase() {
        bookRepository.save(new Book("Título C", "Maria", "Aventura", "OL789"));

        List<Book> result = bookRepository.findByGenreIgnoreCase("aventura");
        assertEquals(1, result.size());
        assertEquals("Aventura", result.get(0).getGenre());
    }

    @Test
    @DisplayName("Deve salvar e buscar livro por gênero parcialmente (ignorando case)")
    void testFindByGenreContainingIgnoreCase() {
        bookRepository.save(new Book("Título D", "Carlos", "Fantasia Medieval", "OL999"));

        List<Book> result = bookRepository.findByGenreContainingIgnoreCase("medieval");
        assertEquals(1, result.size());
        assertTrue(result.get(0).getGenre().contains("Medieval"));
    }
}
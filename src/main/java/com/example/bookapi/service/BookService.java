package com.example.bookapi.service;

import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.external.BookExternalService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookExternalService bookExternalService;

    public BookService(BookRepository bookRepository, BookExternalService bookExternalService) {
        this.bookRepository = bookRepository;
        this.bookExternalService = bookExternalService;
    }

    // MÉTODO PÚBLICO COM CACHE
    @Cacheable(value = "allBooks")
    public List<BookDTO> getAllBooksCached() {
        return getAllBooks();
    }

    // LÓGICA COMPLETA (SEM CACHE)
    public List<BookDTO> getAllBooks() {
        System.out.println("🔍 Buscando todos os livros...");
        List<Book> books = bookRepository.findAll();
        if (!books.isEmpty()) {
            return books.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        List<Book> externalBooks = bookExternalService.fetchAllBooks();
        if (!externalBooks.isEmpty()) {
            bookRepository.saveAll(externalBooks);
            return externalBooks.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        return List.of(); // Lista vazia
    }

    @Cacheable(value = "bookById", key = "#id")
    public BookDTO getBookByIdCached(Long id) {
        return getBookById(id);
    }

    public BookDTO getBookById(Long id) {
        System.out.println("🔍 Buscando livro por ID...");
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            return convertToDTO(optionalBook.get());
        }

        Optional<Book> externalBook = bookExternalService.fetchBookById(id);
        return externalBook.map(book -> {
            bookRepository.save(book);
            return convertToDTO(book);
        }).orElse(null);
    }

    @Cacheable(value = "booksByAuthor", key = "#author")
    public List<BookDTO> getBooksByAuthorCached(String author) {
        return getBooksByAuthor(author);
    }

    public List<BookDTO> getBooksByAuthor(String author) {
        System.out.println("🔍 Buscando livros por autor...");
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (!books.isEmpty()) {
            return books.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        List<Book> externalBooks = bookExternalService.fetchBooksByAuthor(author);
        if (!externalBooks.isEmpty()) {
            bookRepository.saveAll(externalBooks);
            return externalBooks.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        return List.of();
    }

    @Cacheable(value = "booksByGenre", key = "#genre")
    public List<BookDTO> getBooksByGenreCached(String genre) {
        return getBooksByGenre(genre);
    }

    public List<BookDTO> getBooksByGenre(String genre) {
        System.out.println("🔍 Buscando livros por gênero...");
        List<Book> books = bookRepository.findByGenreContainingIgnoreCase(genre);
        if (!books.isEmpty()) {
            return books.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        List<Book> externalBooks = bookExternalService.fetchBooksByGenre(genre);
        if (!externalBooks.isEmpty()) {
            bookRepository.saveAll(externalBooks);
            return externalBooks.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        return List.of();
    }

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getExternalId()
        );
    }
}
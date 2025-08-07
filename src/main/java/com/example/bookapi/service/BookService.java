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

    // ALL BOOKS

    @Cacheable(value = "allBooks")
    public List<BookDTO> getAllBooksCached() {
        return getAllBooks();
    }

    public List<BookDTO> getAllBooks() {
        System.out.println("🔍 Buscando todos os livros...");

        List<Book> books = bookRepository.findAll();
        if (!books.isEmpty()) {
            System.out.println("Encontrado no banco: " + books.size() + " livros.");
            return books.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        List<Book> externalBooks = bookExternalService.fetchAllBooks();
        if (externalBooks.isEmpty()) {
            System.out.println("Nenhum livro encontrado na API externa.");
            return List.of();
        }

        System.out.println("Salvando " + externalBooks.size() + " livros no banco.");
        bookRepository.saveAll(externalBooks);
        return externalBooks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // BOOK BY ID

    @Cacheable(value = "bookById", key = "#id")
    public BookDTO getBookByIdCached(Long id) {
        return getBookById(id);
    }

    public BookDTO getBookById(Long id) {
        System.out.println("Buscando livro por ID: " + id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            System.out.println("Encontrado no banco.");
            return convertToDTO(book.get());
        }

        Optional<Book> externalBook = bookExternalService.fetchBookById(id);
        if (externalBook.isPresent()) {
            System.out.println("Salvando livro no banco.");
            bookRepository.save(externalBook.get());
            return convertToDTO(externalBook.get());
        }

        System.out.println("Livro não encontrado.");
        return null;
    }

    // BOOKS BY AUTHOR

    @Cacheable(value = "booksByAuthor", key = "#author")
    public List<BookDTO> getBooksByAuthorCached(String author) {
        return getBooksByAuthor(author);
    }

    public List<BookDTO> getBooksByAuthor(String author) {
        System.out.println("🔍 Buscando livros por autor: " + author);

        List<Book> booksFromDb = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (!booksFromDb.isEmpty()) {
            System.out.println("Encontrado no banco: " + booksFromDb.size() + " livros.");
            return booksFromDb.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        List<Book> externalBooks = bookExternalService.fetchBooksByAuthor(author);
        if (externalBooks.isEmpty()) {
            System.out.println("Nenhum livro encontrado na API externa.");
            return List.of();
        }

        externalBooks.forEach(book ->
                System.out.println("Livro da API externa: " + book.getTitle() + " | Autor: " + book.getAuthor())
        );

        System.out.println("Salvando " + externalBooks.size() + " livros no banco.");
        bookRepository.saveAll(externalBooks);
        return externalBooks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // BOOKS BY GENRE

    @Cacheable(value = "booksByGenre", key = "#genre")
    public List<BookDTO> getBooksByGenreCached(String genre) {
        return getBooksByGenre(genre);
    }

    public List<BookDTO> getBooksByGenre(String genre) {
        System.out.println("Buscando livros por gênero: " + genre);

        List<Book> books = bookRepository.findByGenreContainingIgnoreCase(genre);
        if (!books.isEmpty()) {
            System.out.println("Encontrado no banco: " + books.size() + " livros.");
            return books.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        List<Book> externalBooks = bookExternalService.fetchBooksByGenre(genre);
        if (externalBooks.isEmpty()) {
            System.out.println("Nenhum livro encontrado na API externa.");
            return List.of();
        }

        System.out.println("Salvando " + externalBooks.size() + " livros no banco.");
        bookRepository.saveAll(externalBooks);
        return externalBooks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // CONVERSÃO
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
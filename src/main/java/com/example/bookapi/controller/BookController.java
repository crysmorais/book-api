package com.example.bookapi.controller;

import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Endpoints para consulta de livros")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID")
    public BookDTO getBookById(
            @Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "ID do livro")
            @PathVariable(name = "id") Long id
    ) {
        return bookService.getBookById(id);
    }

    @GetMapping("/author/{author}")
    @Operation(summary = "Buscar livros por autor")
    public List<BookDTO> getBooksByAuthor(
            @Parameter(name = "author", in = ParameterIn.PATH, required = true, description = "Nome do autor")
            @PathVariable(name = "author") String author
    ) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/genre/{genre}")
    @Operation(summary = "Buscar livros por gênero")
    public List<BookDTO> getBooksByGenre(
            @Parameter(name = "genre", in = ParameterIn.PATH, required = true, description = "Nome do gênero")
            @PathVariable(name = "genre") String genre
    ) {
        return bookService.getBooksByGenre(genre);
    }
}
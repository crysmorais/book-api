package com.example.bookapi.dto;

import com.example.bookapi.model.Book;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa os dados de um livro")
public class BookDTO {

    @Schema(description = "ID do livro", example = "1")
    private Long id;

    @Schema(description = "Título do livro", example = "Dom Casmurro")
    private String title;

    @Schema(description = "Nome do autor", example = "Machado de Assis")
    private String author;

    @Schema(description = "Gênero do livro", example = "Romance")
    private String genre;

    @Schema(description = "Identificador externo (ID da OpenLibrary)", example = "OL123456M")
    private String externalId;

    // Construtores
    public BookDTO() {}

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPublishedDate());
    }

    public BookDTO(Long id, String title, String author, String genre, String externalId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.externalId = externalId;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
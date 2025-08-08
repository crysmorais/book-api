package com.example.bookapi.model;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;
    private String openLibraryId;
    private String externalId;
    private String publishedDate;

    // Construtor padrão (necessário para JPA)
    public Book() {}

    // Construtor com campos principais (parcial)
    public Book(String title, String author, String genre, String openLibraryId) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.openLibraryId = openLibraryId;
    }

    // Construtor completo
    public Book(Long id, String title, String author, String genre, String externalId, String openLibraryId, String publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.externalId = externalId;
        this.openLibraryId = openLibraryId;
        this.publishedDate = publishedDate;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getOpenLibraryId() { return openLibraryId; }
    public void setOpenLibraryId(String openLibraryId) { this.openLibraryId = openLibraryId; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }
}
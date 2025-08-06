package com.example.bookapi.service.external;

import com.example.bookapi.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookExternalService {

    private static final String BASE_URL = "https://openlibrary.org";
    private final RestTemplate restTemplate;

    public BookExternalService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Book> fetchAllBooks() {
        // Exemplo com termo genérico "programming"
        String url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL + "/search.json")
                .queryParam("q", "programming")
                .toUriString();

        OpenLibraryResponse response = restTemplate.getForObject(url, OpenLibraryResponse.class);
        return convertResponseToBooks(response);
    }

    public List<Book> fetchBooksByGenre(String genre) {
        // OpenLibrary não tem endpoint direto para gênero, então usamos como termo de busca
        String url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL + "/search.json")
                .queryParam("subject", genre)
                .toUriString();

        OpenLibraryResponse response = restTemplate.getForObject(url, OpenLibraryResponse.class);
        return convertResponseToBooks(response);
    }

    public List<Book> fetchBooksByAuthor(String author) {
        String url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL + "/search.json")
                .queryParam("author", author)
                .toUriString();

        OpenLibraryResponse response = restTemplate.getForObject(url, OpenLibraryResponse.class);
        return convertResponseToBooks(response);
    }

    public Optional<Book> fetchBookById(Long id) {
        // OpenLibrary ID geralmente é alfanumérico, aqui usamos como exemplo simples
        String olid = "OL" + id + "M"; // Exemplo: OL1M
        String url = BASE_URL + "/books/" + olid + ".json";

        try {
            OpenLibraryBookDetail detail = restTemplate.getForObject(url, OpenLibraryBookDetail.class);
            if (detail != null) {
                Book book = new Book();
                book.setTitle(detail.getTitle());
                book.setAuthor(detail.getBy_statement());
                book.setGenre(""); // OpenLibrary não traz gênero diretamente
                book.setExternalId(olid);
                return Optional.of(book);
            }
        } catch (Exception e) {
            System.out.println("Livro não encontrado na API externa: " + e.getMessage());
        }

        return Optional.empty();
    }

    private List<Book> convertResponseToBooks(OpenLibraryResponse response) {
        if (response == null || response.getDocs() == null) {
            return Collections.emptyList();
        }

        return response.getDocs().stream()
                .map(doc -> {
                    Book book = new Book();
                    book.setTitle(doc.getTitle());
                    book.setAuthor(doc.getAuthorName() != null && !doc.getAuthorName().isEmpty()
                            ? doc.getAuthorName().get(0)
                            : "Desconhecido");
                    book.setGenre(""); // OpenLibrary não retorna gênero diretamente
                    book.setExternalId(doc.getKey());
                    return book;
                })
                .collect(Collectors.toList());
    }
}
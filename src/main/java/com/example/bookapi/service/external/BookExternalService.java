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
        String url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL + "/search.json")
                .queryParam("q", "programming")
                .toUriString();

        OpenLibraryResponse response = restTemplate.getForObject(url, OpenLibraryResponse.class);
        return convertResponseToBooks(response);
    }

    public List<Book> fetchBooksByGenre(String genre) {
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
                .queryParam("q", author)
                .toUriString();

        System.out.println("🔗 Chamando OpenLibrary - URL: " + url);
        OpenLibraryResponse response = restTemplate.getForObject(url, OpenLibraryResponse.class);

        if (response != null && response.getDocs() != null) {
            System.out.println("Quantidade de livros retornados da API: " + response.getDocs().size());
        } else {
            System.out.println("API retornou null ou sem docs");
        }

        return convertResponseToBooks(response).stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Book> fetchBookById(Long id) {
        String olid = "OL" + id + "M";
        String url = BASE_URL + "/books/" + olid + ".json";

        try {
            OpenLibraryBookDetail detail = restTemplate.getForObject(url, OpenLibraryBookDetail.class);
            if (detail != null) {
                Book book = new Book();
                book.setTitle(detail.getTitle());
                book.setAuthor(
                        detail.getBy_statement() != null
                                ? detail.getBy_statement()
                                : "Autor desconhecido"
                );
                book.setGenre("Gênero não informado");
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

                    String author = (doc.getAuthorName() != null && !doc.getAuthorName().isEmpty())
                            ? doc.getAuthorName().get(0)
                            : "Autor desconhecido";
                    book.setAuthor(author);

                    String genre = (doc.getSubject() != null && !doc.getSubject().isEmpty())
                            ? doc.getSubject().get(0)
                            : "Gênero não informado";
                    book.setGenre(genre);

                    book.setExternalId(doc.getKey());
                    return book;
                })
                .collect(Collectors.toList());
    }
}
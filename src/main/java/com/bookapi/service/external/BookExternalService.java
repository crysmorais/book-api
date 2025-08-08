package com.bookapi.service.external;

import com.bookapi.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookExternalService {

    private static final String BASE_URL = "https://openlibrary.org";
    private final RestTemplate restTemplate;

    public BookExternalService() {
        this.restTemplate = new RestTemplate();
    }
    private static final Logger logger = LoggerFactory.getLogger(BookExternalService.class);

    // Mapa de tradução de gêneros
    private static final Map<String, String> GENRE_TRANSLATIONS = Map.ofEntries(
            Map.entry("fiction", "Ficção"),
            Map.entry("horror", "Terror"),
            Map.entry("thriller", "Suspense"),
            Map.entry("fantasy", "Fantasia"),
            Map.entry("romance", "Romance"),
            Map.entry("science fiction", "Ficção científica"),
            Map.entry("mystery", "Mistério"),
            Map.entry("detective and mystery stories", "Romance policial"),
            Map.entry("biography", "Biografia"),
            Map.entry("history", "História"),
            Map.entry("religion", "Religião"),
            Map.entry("Success", "Sucesso"),
            Map.entry("China, fiction", "Ficção"),
            Map.entry("Russia in fictio", "Ficção"),
            Map.entry("Philosophy", "Filosofia"),
            Map.entry("Hannay, richard (fictitious character), fiction", "Ficção"),
            Map.entry("Severe poverty", "Pobreza severa\n"),
            Map.entry("Humorous storie", "História"),
            Map.entry("France -- history -- 19th century -- fiction", "Ficção"),
            Map.entry("Master and servant", "Romance"),
            Map.entry("Aeronautical engineering", "Engenharia aeronáutica"),
            Map.entry("Action/adventure", "Ação/aventura"),
            Map.entry("Autobiography", "Autobiografia"),
            Map.entry("Anatomie humaine", "Anatomia humana\n"),
            Map.entry("Information technology", "Tecnologia da Informação\n"),
            Map.entry("Abstracts", "Resumos"),
            Map.entry("Software engineering", "Engenharia de software\n"),
            Map.entry("catholic church", "Igreja Católica")

    );

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
        logger.info("Chamando OpenLibrary - URL: " + url);
        OpenLibraryResponse response = restTemplate.getForObject(url, OpenLibraryResponse.class);
        if (response != null && response.getDocs() != null) {
            logger.info("Quantidade de livros retornados da API: " + response.getDocs().size());
        } else {
            logger.info("API retornou null ou sem docs");
        }
        return convertResponseToBooks(response);
    }

    public List<Book> fetchBooksByAuthor(String author) {
        String url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL + "/search.json")
                .queryParam("q", author)
                .toUriString();

        logger.info("Chamando OpenLibrary - URL: " + url);
        OpenLibraryResponse response = restTemplate.getForObject(url, OpenLibraryResponse.class);

        if (response != null && response.getDocs() != null) {
            logger.info("Quantidade de livros retornados da API: " + response.getDocs().size());
        } else {
            logger.info("API retornou null ou sem docs");
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
            logger.info("Livro não encontrado na API externa: " + e.getMessage());
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

                    // Buscar gênero extra na rota /works/{id}.json
                    String workId = extractWorkId(doc.getKey());
                    String genre = fetchSubjectsFromWork(workId);
                    book.setGenre(genre);

                    book.setExternalId(doc.getKey());
                    return book;
                })
                .collect(Collectors.toList());
    }

    private String extractWorkId(String key) {
        return key != null && key.contains("/") ? key.substring(key.lastIndexOf("/") + 1) : key;
    }

    private String fetchSubjectsFromWork(String workId) {
        try {
            String url = BASE_URL + "/works/" + workId + ".json";
            WorkDetailResponse response = restTemplate.getForObject(url, WorkDetailResponse.class);

            if (response != null && response.getSubjects() != null && !response.getSubjects().isEmpty()) {
                String originalGenre = response.getSubjects().get(0).toLowerCase();
                return GENRE_TRANSLATIONS.getOrDefault(originalGenre, capitalize(originalGenre));
            }
        } catch (Exception e) {
            logger.info("Erro ao buscar detalhes do work " + workId + ": " + e.getMessage());
        }

        return "Gênero não informado";
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
package com.example.bookapi.controller;

import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void testGetAllBooks() throws Exception {
        BookDTO book = new BookDTO(1L, "Title", "Author", "Genre", "EXTERNAL123");

        // Mock correto baseado no metodo real usado pela controller
        when(bookService.getAllBooksCached()).thenReturn(List.of(book));

        mockMvc.perform(get("/books")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Title"))
                .andExpect(jsonPath("$[0].author").value("Author"));
    }
}
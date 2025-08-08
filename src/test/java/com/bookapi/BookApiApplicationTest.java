package com.bookapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BookApiApplicationTest {

    @Test
    void contextLoads() {
        // Verifica se o contexto Spring sobe corretamente
    }
}
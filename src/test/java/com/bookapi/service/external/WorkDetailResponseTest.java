package com.bookapi.service.external;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkDetailResponseTest {

    @Test
    void testSetAndGetSubjects() {
        WorkDetailResponse response = new WorkDetailResponse();

        List<String> subjects = List.of("Fiction", "Thriller", "Drama");
        response.setSubjects(subjects);

        assertEquals(subjects, response.getSubjects());
        assertEquals(3, response.getSubjects().size());
        assertEquals("Fiction", response.getSubjects().get(0));
    }
}
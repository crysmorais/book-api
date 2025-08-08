package com.example.bookapi.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.cache.CacheManager;

@WebMvcTest(CacheController.class)
public class CacheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CacheManager cacheManager;

    @Test
    void testClearCacheEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cache/clear"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("cache")
                ));
    }
}
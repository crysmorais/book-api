package com.bookapi.config;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.junit.jupiter.api.Assertions.*;

class RedisConfigTest {

    @Test
    void testRedisConnectionFactoryCreation() {
        RedisConfig config = new RedisConfig();
        LettuceConnectionFactory factory = config.redisConnectionFactory();

        assertNotNull(factory);
        assertEquals("redis", factory.getHostName());
        assertEquals(6379, factory.getPort());
    }

    @Test
    void testRedisTemplateConfiguration() {
        RedisConfig config = new RedisConfig();
        RedisTemplate<String, Object> template = config.redisTemplate();

        assertNotNull(template);
        assertTrue(template.getKeySerializer() instanceof StringRedisSerializer);
        assertTrue(template.getValueSerializer() instanceof GenericJackson2JsonRedisSerializer);
    }
}
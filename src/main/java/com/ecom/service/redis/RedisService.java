package com.ecom.service.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Service
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public RedisService(StringRedisTemplate stringRedisTemplate,
                        ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    public void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void setValueWithExpiry(String key, String value, Duration duration) {
        stringRedisTemplate.opsForValue().set(key, value, duration);
    }

    public void deleteValue(String key) {
        stringRedisTemplate.delete(key);
    }

    public <T> void setObjectWithExpiry(
            String key,
            T object,
            Duration duration) {

        try {
            String json = objectMapper.writeValueAsString(object);

            stringRedisTemplate.opsForValue()
                    .set(key, json, duration);

        } catch (JacksonException e) {
            throw new RuntimeException("Could not convert object to JSON", e);
        }
    }

    public <T> T getObject(
            String key,
            Class<T> clazz) {

        String json = stringRedisTemplate.opsForValue().get(key);

        if (json == null) {
            return null;
        }

        try {
            return objectMapper.readValue(json, clazz);

        } catch (JacksonException e) {
            throw new RuntimeException("Could not convert JSON to object", e);
        }
    }
}
package com.ecom.service.redis;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisService(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void setValue(String key , String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
    public void setValueWithExpiry(String key , String value , Duration duration){
        stringRedisTemplate.opsForValue().set(key, value , duration);
    }

    public void deleteValue(String key){
        stringRedisTemplate.delete(key);
    }
}

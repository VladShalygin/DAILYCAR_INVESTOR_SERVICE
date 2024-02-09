package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.services.RedisService;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}

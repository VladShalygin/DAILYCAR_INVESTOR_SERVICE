package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.services.RedisService;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new NotFoundException("Запись по ключу не найдена!");
        }
    }

    public Boolean delete(String key) throws NotFoundException {
        Boolean deletedKeys = redisTemplate.delete(key);
        if (Boolean.FALSE.equals(deletedKeys)) {
            throw new NotFoundException("Запись по ключу не найдена!");
        }
        return deletedKeys;
    }
}

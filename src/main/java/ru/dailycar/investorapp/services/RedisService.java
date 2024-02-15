package ru.dailycar.investorapp.services;

public interface RedisService {

    void set(String key, String value);

    String get(String key);

    Boolean delete(String key);
}

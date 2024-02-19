package ru.dailycar.investorapp.services;

import java.util.concurrent.TimeoutException;

public interface RedisService {

    void set(String key, String value) throws TimeoutException;

    String get(String key);

    Boolean delete(String key);
}

package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.services.AuthCodeService;

@Service
@RequiredArgsConstructor
public class AuthCodeServiceImpl implements AuthCodeService {

    private final RedisService service;

    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String createCode(String username) {

        //проверить на существование

        //захэшировать инфу инфу

        //зарандомить код

        //сохранить в редис

        //вернуть в токене захэшированые

        return null;
    }

    @Override
    public Boolean checkCode() {
        return null;
    }
}

package ru.dailycar.investorapp.services;

public interface AuthCodeService {

    String createCode(String username);

    Boolean checkCode();

    Integer generateCode();
}

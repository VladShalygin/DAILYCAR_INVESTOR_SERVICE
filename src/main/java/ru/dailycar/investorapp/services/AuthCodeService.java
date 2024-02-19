package ru.dailycar.investorapp.services;

import org.apache.coyote.BadRequestException;
import ru.dailycar.investorapp.entities.CodeType;

import java.util.concurrent.TimeoutException;

public interface AuthCodeService {

    String createCode(String username, CodeType type) throws TimeoutException;

    Boolean checkCode(String username, String code, CodeType type) throws BadRequestException;

    Integer generateCode();

    Boolean deleteCode(String username, CodeType type);

}

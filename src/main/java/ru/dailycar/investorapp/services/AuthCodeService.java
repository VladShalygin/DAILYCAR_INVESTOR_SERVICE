package ru.dailycar.investorapp.services;

import org.apache.coyote.BadRequestException;
import ru.dailycar.investorapp.entities.CodeType;

public interface AuthCodeService {

    String createCode(String username, CodeType type);

    Boolean checkCode(String username, String code, CodeType type) throws BadRequestException;

    Integer generateCode();

    Boolean deleteCode(String username, CodeType type);

}

package ru.dailycar.investorapp.exceptions;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists() {
        super("Пользователь с такими данными уже существует!");
    }

}



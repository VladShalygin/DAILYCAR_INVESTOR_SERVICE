package ru.dailycar.investorapp.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BasicErrorResponse {

    private final String message;
}

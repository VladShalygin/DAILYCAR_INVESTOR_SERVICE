package ru.dailycar.investorapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotificationSendingError extends RuntimeException {
    public NotificationSendingError(String message) {
        super(message);
    }

    public NotificationSendingError(String message, Throwable cause) {
        super(message, cause);
    }
}

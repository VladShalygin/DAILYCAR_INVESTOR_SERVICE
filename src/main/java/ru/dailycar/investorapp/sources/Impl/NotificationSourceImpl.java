package ru.dailycar.investorapp.sources.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.dailycar.investorapp.dto.SendNotificationRequest;
import ru.dailycar.investorapp.entities.Notification;
import ru.dailycar.investorapp.exceptions.NotificationSendingError;
import ru.dailycar.investorapp.sources.NotificationSource;

@Component
@RequiredArgsConstructor
public class NotificationSourceImpl implements NotificationSource {

    @Value("${link.notification}")
    private String url;

    @Override
    public void sendNotification(SendNotificationRequest request) {
        try {
            ResponseEntity<Notification> result =  RestClient.create().get()
                    .uri(url)
                    .retrieve()
                    .toEntity(Notification.class);

            if (result.getStatusCode() == HttpStatus.OK) {
                throw  new NotificationSendingError("Сервис отправки уведомлений ответил ошибкой!");
            }

        } catch (Exception e) {
            throw new NotificationSendingError("Сервис отправки уведомлений ответил ошибкой!");
        }
    }
}

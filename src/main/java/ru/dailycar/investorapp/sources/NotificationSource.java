package ru.dailycar.investorapp.sources;

import org.springframework.http.HttpStatusCode;
import ru.dailycar.investorapp.dto.SendNotificationRequest;

public interface NotificationSource {

    String sendNotification(SendNotificationRequest request);

}

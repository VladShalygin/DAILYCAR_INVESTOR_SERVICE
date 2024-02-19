package ru.dailycar.investorapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
public class SendNotificationRequest {
    @NotBlank
    private String destinationId;

    @NotBlank
    private String destinationContact;

    @Nullable
    private String Subject;

    @NotBlank
    private String message;

    @NotBlank
    private String type;

    @NotBlank
    private String creatorId;

     public static SendNotificationRequest createCodeNotification(String username, String id, Integer code) {
        return SendNotificationRequest
                .builder()
                .creatorId("system")
                .destinationId(id)
                .destinationContact(username)
                .message("Добрый день! \n\n" +
                        "Ваш код подтверждения для кабинета инвестора DailyCar: " + code.toString() + "\n" +
                        "Если действие осуществляли не вы, то свяжитесь с нами по номеру +7 (843) 5-285-285.\n\n" +
                        "Хорошего вам дня!")
                .Subject("Подтвержение в кабинете инвестора")
                .type(username.contains("@") || username.contains("%40") ? "MAIL" : "SMS")
                .build();
    }
}

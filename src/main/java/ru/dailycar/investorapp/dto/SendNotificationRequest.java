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
                .message("Ваш код для кабинета инвестора - " + code.toString())
                .Subject("Подтвержение в кабинете инвестора")
                .type(username.contains("@") ? "MAIL" : "SMS")
                .build();
    }
}

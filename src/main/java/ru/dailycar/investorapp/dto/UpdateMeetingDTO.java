package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;
import ru.dailycar.investorapp.entities.MeetingType;

@Data
public class UpdateMeetingDTO {

    @Nullable
    @Schema(description = "ID пользователя, приглашенного на встречу")
    private String userId;

    @Nullable
    @Schema(description = "Дата встречи")
    private Long date;

    @Nullable
    @Schema(description = "Описание встречи, заметки")
    private String description;

    @Nullable
    @Schema(description = "Тип встречи")
    private String type;

    @Nullable
    @Schema(description = "Статус встречи")
    private String status;

    @Nullable
    @Schema(description = "ID договора")
    private String contractId;

    @Nullable
    @Schema(description = "ID заявки")
    private String bidId;
}

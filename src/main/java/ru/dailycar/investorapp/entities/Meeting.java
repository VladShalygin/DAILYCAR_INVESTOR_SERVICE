package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

@Data
@Document
@Builder
@Schema(description = "Сущность встречь")
public class Meeting {

    @Id
    @Schema(description = "ID встречи")
    private String id;

    @Schema(description = "ID пользователя, приглашенного на встречу")
    private String userId;

    @Schema(description = "Дата встречи")
    private Long date;

    @Schema(description = "Описание встречи, заметки")
    private String description;

    @Schema(description = "Тип встречи")
    private MeetingType type;

    @Schema(description = "Статус встречи")
    private MeetingStatus status;

    @Nullable
    @Schema(description = "ID договора")
    private String contractId;

    @Nullable
    @Schema(description = "ID заявки")
    private String bidId;
}

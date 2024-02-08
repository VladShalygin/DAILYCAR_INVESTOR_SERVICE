package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.lang.Nullable;
import ru.dailycar.investorapp.entities.MeetingType;

@Data
public class CreateMeetingDto {

    @NotBlank
    @Schema(description = "ID пользователя, приглашенного на встречу")
    private String userId;

    @Positive
    @Schema(description = "Дата встречи")
    private Long date;

    @Schema(description = "Описание встречи, заметки")
    private String description;

    @NotBlank
    @Schema(description = "Тип встречи")
    private String type;

    @Nullable
    @Schema(description = "ID договора")
    private String contractId;

    @Nullable
    @Schema(description = "ID заявки")
    private String bidId;

}

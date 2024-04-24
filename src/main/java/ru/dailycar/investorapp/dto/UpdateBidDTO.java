package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.lang.Nullable;
import ru.dailycar.investorapp.entities.BidStatus;

import java.lang.reflect.Field;
import java.util.Arrays;

@Data
@Schema(description = "Сущность для обновления заявки на инвестицию")
public class UpdateBidDTO {

    @Nullable
    @Schema(description = "ID пользователя, котрому принадлежит заявка", example = "64804f7ab3afb023c6b9d397")
    private String userId;

    @Nullable
    @Schema(description = "Сумма инвестиции", example = "500000")
    private int amount;

    @Nullable
    @Schema(description = "Статус заявки", example = "CREATED")
    private BidStatus status;

    @Nullable
    @Schema(description = "Описание заявки", example = "Некое описание, для случаев когда манагеру надо что-то пометить или запомнить")
    private String description;

    @Nullable
    @Schema(description = "Тип будущего контракта", example = "CAR")
    private String type;

}

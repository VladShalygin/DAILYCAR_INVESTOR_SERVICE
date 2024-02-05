package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;
import ru.dailycar.investorapp.entities.ContractStatus;

@Schema(description = "Сущность для обновления")
public class UpdateContractDTO {

    @Schema(description = "Статус договора", example = "EXPIRATION")
    @Nullable
    private ContractStatus status;

    @Schema(description = "Дата конца договора", example = "1705129595000")
    @Nullable
    private Long dateExpiration;

}

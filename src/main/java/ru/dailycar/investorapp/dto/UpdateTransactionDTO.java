package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;
import ru.dailycar.investorapp.entities.TransactionStatus;
import ru.dailycar.investorapp.entities.TransactionType;
@Data
@Schema(description = "Сущность для обновления транзакции инвестора")
public class UpdateTransactionDTO {

    @Schema(description = "Сумма транзакции", example = "500000")
    @Nullable
    private int amount;

    @Schema(description = "Статус транзакции", example = "PROCESSED")
    @Nullable
    private TransactionStatus status;
}

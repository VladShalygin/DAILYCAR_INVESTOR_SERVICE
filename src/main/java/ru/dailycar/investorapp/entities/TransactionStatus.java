package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус платежной транзакции")
public enum TransactionStatus {
    @Schema(description = "Создана")
    CREATED,

    @Schema(description = "В работе")
    PROCESSED,

    @Schema(description = "Совершена")
    COMPLETED
}

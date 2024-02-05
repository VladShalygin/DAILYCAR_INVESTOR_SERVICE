package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тип транзакции")
public enum TransactionType {

    @Schema(description = "Инвестиция")
    INVESTMENT,

    @Schema(description = "Выплата")
    PAYOUT
}

package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус заявки")
public enum BidStatus {
    @Schema(description = "Создана")
    CREATED,

    @Schema(description = "В работе")
    PROCESSED,

    @Schema(description = "Удалена")
    DELETED,

    @Schema(description = "Выполнена")
    COMPLETED
}

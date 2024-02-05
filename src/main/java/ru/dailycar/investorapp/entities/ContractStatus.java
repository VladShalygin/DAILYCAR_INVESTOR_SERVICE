package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статусы договора")
public enum ContractStatus {
    @Schema(description = "Активный")
    ACTIVE,
    @Schema(description = "Истек")
    EXPIRATION
}

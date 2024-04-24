package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тип договора")
public enum ContractType {
    @Schema(description = "По договору в залоге машина")
    CAR,
    @Schema(description = "По договору в залоге земля")
    LAND,
    @Schema(description = "По договору в залоге земля")
    AGENT,
    @Schema(description = "Расторжение")
    TERMINATE
}

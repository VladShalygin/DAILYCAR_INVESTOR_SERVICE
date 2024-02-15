package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тип процентов")
public enum PercentType {

    @Schema(description = "По договору с залогом")
    CONTRACT,

    @Schema(description = "Агентский")
    AGENT

}

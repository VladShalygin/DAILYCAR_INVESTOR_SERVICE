package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;

public enum InvestorType {
    @Schema(description = "Физическое лицо")
    LEGAL,

    @Schema(description = "Юридическое лицо")
    INDIVIDUAL
}

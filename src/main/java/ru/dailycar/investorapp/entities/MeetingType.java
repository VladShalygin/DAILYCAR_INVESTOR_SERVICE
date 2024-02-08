package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;

public enum MeetingType {

    @Schema(description = "Подписание контракта")
    SIGNING_CONTRACT,

    @Schema(description = "Подписание дополнительного соглашения")
    SIGNING_AGREEMENT,

    @Schema(description = "Расторжение договора")
    TERMINATION_CONTRACT

}

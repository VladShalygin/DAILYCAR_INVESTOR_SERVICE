package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тип зявки")
public enum BidType {
    @Schema(description = "По договору в залоге машина")
    CAR,
    @Schema(description = "По договору в залоге земля")
    LAND,
    @Schema(description = "По договору в залоге земля")
    AGENT,
    @Schema(description = "Расторжение")
    TERMINATE,
    @Schema(description = "Смена залогвого имущества")
    CHANGE_PLEDGE,
    @Schema(description = "Обновление паспорта")
    UPDATE_PASSPORT


}

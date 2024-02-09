package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateAgentPercentageDTO {

    @NotBlank
    @Schema(description = "Названаие серии процентов")
    private String name;

    @Positive
    @Schema(description = "Проценты первого уровня")
    private Double firstLvl;

    @Positive
    @Schema(description = "Проценты второго уровня")
    private Double secondLvl;
}

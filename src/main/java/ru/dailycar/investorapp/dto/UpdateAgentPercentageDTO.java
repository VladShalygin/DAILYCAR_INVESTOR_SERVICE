package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class UpdateAgentPercentageDTO {

    @Nullable
    @Schema(description = "Названаие серии процентов")
    private String name;

    @Nullable
    @Schema(description = "Проценты первого уровня")
    private Double firstLvl;

    @Nullable
    @Schema(description = "Проценты второго уровня")
    private Double secondLvl;
}

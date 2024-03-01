package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePercentDto {
    @NotNull
    @Schema(description = "Тип платежа")
    private String type;

    @Positive
    @Schema(description = "Сумма процента")
    private Double amount;

    @NotBlank
    @Schema(description = "Id договора, к котому относится данный процент")
    private String contractId;

    @NotBlank
    @Schema(description = "Id договора, к котому относится данный процент")
    private Integer number;

    @NotBlank
    @Schema(description = "Дата расчета процента")
    private Long date;

}

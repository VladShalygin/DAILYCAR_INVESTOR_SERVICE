package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
public class CreatePercentDto {
    @NotNull
    @Schema(description = "Тип платежа")
    private String type;

    @NotNull
    @Schema(description = "Статус - оплачен/создан")
    private String status;

    @Positive
    @Schema(description = "Сумма процента")
    private Integer amount;

    @NotBlank
    @Schema(description = "Id договора, к котому относится данный процент")
    private String contractId;

    @Nullable
    @Schema(description = "Привязка к расчитанному проценту у выплаченного платежа")
    private String calculatedPercentId;
}

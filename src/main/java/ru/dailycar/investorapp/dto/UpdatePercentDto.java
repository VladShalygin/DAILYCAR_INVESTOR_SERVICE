package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;
import ru.dailycar.investorapp.entities.PercentStatus;
import ru.dailycar.investorapp.entities.PercentType;

@Data
@Builder
public class UpdatePercentDto {

    @Nullable
    @Schema(description = "Тип платежа")
    private PercentType type;

    @Nullable
    @Schema(description = "Сумма процента")
    private Double amount;

    @Nullable
    @Schema(description = "Дата создания этого процента")
    private Long timestamp;

    @Nullable
    @Schema(description = "Id договора, к котому относится данный процент")
    private String contractId;

}

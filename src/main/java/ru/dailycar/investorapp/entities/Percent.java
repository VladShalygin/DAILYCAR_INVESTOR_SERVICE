package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

@Data
@Document
@Builder
@Schema(description = "Сущность процента")
public class Percent {

    @Id
    private String id;

    @NotNull
    @Schema(description = "Тип платежа")
    private PercentType type;

    @Positive
    @Schema(description = "Сумма процента")
    private Double amount;

    @Positive
    @Schema(description = "Дата создания этого процента")
    private Long timestamp;

    @Positive
    @Schema(description = "Дата создания этого процента")
    private Integer number;

    @NotBlank
    @Schema(description = "Id договора, к котому относится данный процент")
    private String contractId;


}

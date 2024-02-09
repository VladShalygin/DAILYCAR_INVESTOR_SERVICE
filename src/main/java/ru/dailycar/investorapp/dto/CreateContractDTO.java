package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.lang.Nullable;
import ru.dailycar.investorapp.entities.ContractType;

@Data
@Schema(description = "Сущность создаваемого договора" )
public class CreateContractDTO {

    @Schema(description = "ID инвестора, которму принадлежит договор", example = "64804f7ab3afb023c6b9d397" )
    @NotBlank(message = "userId не должен быть пустым или null" )
    private String userId;

    @Schema(description = "ID предмета, который явлется залогом", example = "64804f7ab3afb023c6b9d398" )
    @NotBlank(message = "pledgeId не должен быть пустым или null" )
    private String pledgeId;

    @Schema(description = "Сумма инвестиции по договору", example = "500000" )
    @Positive(message = "amount должен быть больше 0")
    private Integer amount;

    @Schema(description = "Процентная ставка по договору", example = "20" )
//    @Positive(message = "percent Должен быть больше от 1 до 100")
//    @Max(100)
    @Nullable
    private Integer percent;

    @Schema(description = "Тип договора", example = "CAR")
    @NotNull(message = "type не должен быть null")
    private ContractType type;

    @Schema(description = "Id процентных ставок по агенскому договору")
    @Nullable
    private String agentPercentageId;

}

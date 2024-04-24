package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(description = "Сущность создаваемой заявки")
public class CreateBidDTO {

    @Schema(description = "ID инвестора, которму принадлежит заявка", example = "64804f7ab3afb023c6b9d397" )
    @NotBlank(message = "userId не должен быть пустым или null" )
    private String userId;

    @Schema(description = "Сумма инвестиции или выплаты по заявке", example = "500000" )
    @PositiveOrZero(message = "amount должен быть больше или равно 0")
    private int amount;

    @Schema(description = "Тип будущего контракта", example = "CAR")
    private String type;

}

package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.dailycar.investorapp.entities.TransactionType;

@Data
@Schema(description = "Сущность создаваемой транзакции")
public class CreateTransactionDTO {

    @Schema(description = "Сумма создаваемой инвестиции или выплаты", example = "500000")
    @Positive(message = "amount должен быть больше 0")
    private int amount;

    @Schema(description = "ID пользователя, которму принадлежит данная транзакция", example = "64804f7ab3afb023c6b9d397")
    @NotBlank(message = "userId не должен быть пустым или null" )
    private String userId;

    @Schema(description = "ID договра, по которому проходит данная транзакция", example = "64804f7ab3afb023c6b9d399")
    @NotBlank(message = "contractId не должен быть пустым или null" )
    private String contractId;

    @Schema(description = "Тип инвестиции", example = "INVESTMENT")
    @NotNull(message = "type не должен быть null")
    private TransactionType type;

    @Schema(description = "Id реквизитов", example = "64804f7ab3afb023c6b9d5464asd")
    @NotNull(message = "requisiteId не должен быть null")
    private String requisiteId;


}

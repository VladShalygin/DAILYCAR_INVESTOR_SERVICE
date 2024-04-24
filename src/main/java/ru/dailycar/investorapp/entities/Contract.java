package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

@Data
@Document
@Builder(toBuilder = true)
@Schema(description = "Сущность договора")
public class Contract {

    @Id
    @Schema(description = "ID договора", example = "64804f7ab3afb023c6b9d397")
    private String id;

    @Schema(description = "ID пользователя", example = "64804f7ab3afb023c6b9d334")
    private String userId;

    @Schema(description = "ID предмета, который явлется залогом", example = "64804f7ab3afb023c6b9d398" )
    private String pledgeId;

    @Schema(description = "Дата создания договра", example = "1705129595000")
    private Long dateCreate;

    @Schema(description = "Дата конца договра", example = "1705129595000")
    private Long dateExpiration;

    @Schema(description = "Статус договора", example = "CREATED")
    private ContractStatus status;

    @Schema(description = "Тип договора", example = "LAND")
    private ContractType type;

    @Schema(description = "Сумма инвестиции по договору", example = "500000")
    @Nullable
    private Integer amount;

    @Schema(description = "Процентная ставка по договору", example = "20")
    @Nullable
    private Integer percent;

    @Schema(description = "Id процентных ставок по договору агента")
    private String agentPercentageId;

    @Schema(description = "Путь до электронного договора")
    private String path;

    @Schema(description = "Id договора агента")
    private String parentContractId;

    @Schema(description = "Номер контракта")
    private String number;

    @Schema(description = "Номер обременения")
    private String encumbranceNumber;

}

package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@Schema(description = "Сущность платежной транзакции")
public class Transaction {

    @Id
    @Schema(description = "ID транзакции", example = "64804f7ab3afb023c6b9d2131")
    private String id;

    @Schema(description = "ID инвестора, которму принадлежит данная транзакция", example = "64804f7ab3afb023c6b9d39678")
    private String userId;

    @Schema(description = "ID договора по которому осуществлена транзакция", example = "64804f7ab3afb023c6b9d3fh")
    private String contractId;

    @Schema(description = "Дата совершения платежа", example = "1705129595000")
    private Long date;

    @Schema(description = "Сумма совершенной транзакции", example = "500000")
    private Integer amount;

    @Schema(description = "Тип транзакции", example = "INVESTMENT")
    private TransactionType type;

    @Schema(description = "Статус транзакции", example = "CREATED")
    private TransactionStatus status;

}

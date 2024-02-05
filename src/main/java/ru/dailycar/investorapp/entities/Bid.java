package ru.dailycar.investorapp.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@Schema(description = "Сущность заявки по инвестиции")
public class Bid {

    @Id
    @Schema(description = "ID заявки на инвестицию", example = "64804f7ab3afb023c6b9d397")
    private String id;

    @Schema(description = "ID пользователя, которму принадлежит заявка", example = "64804f7ab3afb023c6b9d323")
    private String userId;

    @Schema(description = "Сумма инвестиции", example = "500000")
    private Integer amount;

    @Schema(description = "Статус заявки", example = "COMPLETED")
    private BidStatus status;

    @Schema(description = "Описание заявки", example = "Пометка или что-то ещё")
    private String description;

    @Schema(description = "Тип будущего контракта", example = "CAR")
    private ContractType type;

}

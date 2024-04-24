package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Schema(description = "Сущность процентов агента")
public class AgentPercentage {

    @Id
    @Schema(description = "Id процентов агента")
    private String id;

    @Schema(description = "Названаие серии процентов")
    private String name;

    @Schema(description = "Проценты первого уровня")
    private int firstLvl;

    @Schema(description = "Проценты второго уровня")
    private int secondLvl;

}

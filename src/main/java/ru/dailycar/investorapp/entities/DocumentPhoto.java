package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@Schema(description = "Сущность фотографии документа инвестора")
public class DocumentPhoto {

    @Id
    @Schema(description = "ID фото договора", example = "64804f7ab3afb023c6b9d397")
    private String id;

    @Schema(description = "ID инвестора, чьи фото документов храняться", example = "64804f7ab3afb023c6b9d123")
    private String userId;

    @Schema(description = "Путь до файла", example = "/home/autocars...")
    private String path;

    @Schema(description = "Статус фото")
    private DocumentPhotoStatus status;

    @Schema(description = "Дата загрузки", example = "1705129595000")
    private Long dateUpload;

}

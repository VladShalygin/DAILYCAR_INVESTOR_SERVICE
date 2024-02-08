package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.lang.Nullable;
@Data
@Schema(description = "Создание банковских реквизитов")
public class CreateBankRequisitesDTO {

    @Schema(description = "Id пользователя")
    @NotBlank
    public String userId;

    @Schema(description = "Для ФЛ: ФИО, для ЮЛ: Наименование организации")
    @NotBlank
    public String name;

    @NotBlank
    @Schema(description = "Инн получателя")
    public String inn;

    @Schema(description = "ТОЛЬКО ДЛЯ ЮЛ: КПП Получателя")
    @Nullable
    public String kpp;

    @NotBlank
    @Schema(description = "Наименования банка получателя")
    public String bankName;

    @NotBlank
    @Schema(description = "Инн банка")
    public String bankInn;

    @NotBlank
    @Schema(description = "Кпп банка")
    public String bankKpp;

    @NotBlank
    @Schema(description = "Бик банка")
    public String bankBik;

    @NotBlank
    @Schema(description = "Корреспондентский счёт банка")
    public String bankCorrespondentAccount;

    @NotBlank
    @Schema(description = "Расчетный счёт получатля")
    public String account;
}

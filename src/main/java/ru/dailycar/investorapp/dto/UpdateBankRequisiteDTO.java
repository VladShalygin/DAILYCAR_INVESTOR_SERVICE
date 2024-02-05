package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

public class UpdateBankRequisiteDTO {

    @Schema(description = "Для ФЛ: ФИО, для ЮЛ: Наименование организации")
    private String name;

    @Schema(description = "Инн получателя")
    private String inn;

    @Schema(description = "ТОЛЬКО ДЛЯ ФЛ: КПП Получателя")
    @Nullable
    private String kpp;

    @Schema(description = "Наименования банка получателя")
    private String bankName;

    @Schema(description = "Инн банка")
    private String bankInn;

    @Schema(description = "Кпп банка")
    private String bankKpp;

    @Schema(description = "Бик банка")
    private String bankBik;

    @Schema(description = "Корреспондентский счёт банка")
    private String bankCorrespondentAccount;

    @Schema(description = "Расчетный счёт получатля")
    private String account;

}

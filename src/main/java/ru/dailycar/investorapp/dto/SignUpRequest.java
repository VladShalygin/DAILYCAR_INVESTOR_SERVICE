package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.dailycar.investorapp.entities.GenderType;
import ru.dailycar.investorapp.entities.InvestorType;


@Data
@Schema(description = "Сущность создаваемого пользователя")
public class SignUpRequest {

    @Schema(description = "Имя создаваемого пользователя", example = "Вася")
    @NotBlank(message = "name не должно быть пустым")
    private String name;

    @Schema(description = "Отчествого создаваемого пользователя", example = "Петрович")
    @NotBlank(message = "secondName не должно быть пустым")
    private String secondName;

    @Schema(description = "Фамилия создаваемого пользователя", example = "Иванов")
    @NotBlank(message = "surname не должно быть пустым")
    private String surname;

    @Schema(description = "Номер телефона пользователя", example = "89994446677")
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Не корректный номер телефона")
    private String phoneNumber;

    @Schema(description = "Электронная почта пользователя", example = "example@test.com")
    @Email(message = "email должен быть корректным")
    private String email;

    @Schema(description = "Реферальный код инвестора, который пригласил данного пользователя", example = "DSC2132FFa23")
    @NotBlank(message = "parentReferralCode не должен быть null")
    private String parentReferralCode;

    @Schema(description = "Пароль пользователя")
    @NotBlank(message = "password не должен быть null")
    private String password;

    @Schema(description = "Юридическое/Физическое лицо")
    @NotBlank(message = "type не должен быть null")
    private String type;

    @Schema(description = "Код подтверждения почты")
    @NotBlank(message = "emailCode не должен быть null")
    private String emailCode;

    @Schema(description = "Код подтверждения телефон")
    @NotBlank(message = "phoneCode не должен быть null")
    private String phoneCode;

//    @Schema(description = "Пол инвестора")
//    @NotBlank
//    private GenderType gender;

}



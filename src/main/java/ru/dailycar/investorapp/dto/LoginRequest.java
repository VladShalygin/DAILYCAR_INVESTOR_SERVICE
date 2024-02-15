package ru.dailycar.investorapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Номер телефона или Почта не должно быть пустым")
    private String username;

    @NotBlank(message = "Пароль является обязательным полем")
    private String password;

    @NotBlank(message = "Код является обязательным полем")
    private String code;


}

package ru.dailycar.investorapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {

    @NotBlank(message = "Username обязателен")
    private String username;

    @NotBlank(message = "Старый пароль обязателен")
    private String oldPassword;

    @NotBlank(message = "Новый пароль обязателен")
    private String newPassword;

}

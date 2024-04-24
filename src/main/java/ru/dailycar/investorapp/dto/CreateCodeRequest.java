package ru.dailycar.investorapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class CreateCodeRequest {

    @NotBlank(message = "Номер телефона или Почта не должно быть пустым")
    private String username;

    @Nullable
    private String password;

    @NotBlank(message = "Тип является обязательным полем")
    private String type;

}

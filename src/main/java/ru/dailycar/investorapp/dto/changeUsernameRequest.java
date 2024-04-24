package ru.dailycar.investorapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class changeUsernameRequest {

    @NotBlank
    String codeOldUsername;

    @NotBlank
    String codeNewUsername;

    @NotBlank
    String oldUsername;

    @NotBlank
    String newUsername;
}

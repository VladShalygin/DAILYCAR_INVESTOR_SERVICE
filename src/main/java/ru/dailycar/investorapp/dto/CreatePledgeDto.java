package ru.dailycar.investorapp.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePledgeDto {

    @NotBlank
    private final String type;

    @NotBlank
    private final String sid;

    @Nullable
    private final String number;

}

package ru.dailycar.investorapp.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class UpdatePledgeDto {

    @Nullable
    private final String type;

    @Nullable
    private final String sid;

    @Nullable
    private final String number;

}

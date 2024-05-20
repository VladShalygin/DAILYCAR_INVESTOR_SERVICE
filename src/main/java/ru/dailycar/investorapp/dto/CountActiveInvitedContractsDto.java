package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountActiveInvitedContractsDto {
    private int count;
}

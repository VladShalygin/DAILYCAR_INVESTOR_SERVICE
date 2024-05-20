package ru.dailycar.investorapp.dto;

import lombok.Builder;

@Builder
public class ContractsCounts {
    private int dayCreatedContracts;
    private int dayTerminatedContracts;
    private int weekCreatedContracts;
    private int weekTerminatedContracts;
    private int monthCreatedContracts;
    private int montTerminatedContracts;
    private int yearCreatedContracts;
    private int yearTerminatedContracts;
}

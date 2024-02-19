package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAgentPanelDto {

    private Double countedAllTime;
    private Double paidAllTime;
    private Double availableForPay;
    private Double countedForMonth;

}

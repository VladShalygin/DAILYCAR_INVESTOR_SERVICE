package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;
import ru.dailycar.investorapp.entities.InvestorType;

@Builder
@Data
public class ActiveInvestorInfo {
    private String userId;
    private String phoneNumber;
    private String surnameWithInitials;
    private long birthday;
    private InvestorType investorType;
}


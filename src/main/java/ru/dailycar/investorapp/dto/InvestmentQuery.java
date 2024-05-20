package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestmentQuery {
    private long date;
    private String userId;
    private String bidId;
    private int amount;
    private String surnameWithInitials;
    private String phoneNumber;
}

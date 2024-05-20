package ru.dailycar.investorapp.dto;

import lombok.Data;

@Data
public class InvestorsCount {
    private int allInvestors;
    private int dayInvestors;
    private int weekInvestors;
    private int monthInvestors;
    private int yearInvestors;
}

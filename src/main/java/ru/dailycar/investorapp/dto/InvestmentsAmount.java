package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestmentsAmount {
    private int allInvest;// мы берем все активные инвестиции и и считаем их сумму транзакций
    private int dayInvestments;
    private int weekInvestments;
    private int monthInvestments;
    private int yearInvestments;
}

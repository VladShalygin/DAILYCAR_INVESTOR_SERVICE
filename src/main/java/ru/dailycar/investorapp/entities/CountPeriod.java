package ru.dailycar.investorapp.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountPeriod {
    private int balance;
    private Long dateStart;
    private Long dateEnd;
    private int percent;
    private Long daysInYear;
    private Long daysInMonth;
}

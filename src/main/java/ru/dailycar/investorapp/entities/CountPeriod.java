package ru.dailycar.investorapp.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountPeriod {
    private Integer balance;
    private Long dateStart;
    private Long dateEnd;
    private Integer percent;
    private Integer daysInYear;
    private Integer daysInMonth;
    private Integer number;
}

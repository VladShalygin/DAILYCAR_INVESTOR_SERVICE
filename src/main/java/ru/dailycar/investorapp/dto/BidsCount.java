package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BidsCount {

    private int newBids;
    private int workBids;
    private int completedBids;

}

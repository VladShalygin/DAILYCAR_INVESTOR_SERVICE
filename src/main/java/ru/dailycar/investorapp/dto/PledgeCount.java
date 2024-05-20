package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PledgeCount {
    private int availablePledge;
    private int unavailablePledge;
    private int inPledge;
    private int bookedPledge;
}

package ru.dailycar.investorapp.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class MainPageTable {
    private InvestmentsAmount investmentsAmount;
    private BidsCount bidsCount;
    private PledgeCount pledgeCount;
    private InvestorsCount investorsCount;
    private ContractsCounts agentContractsCounts;
    private ContractsCounts investmentsContractsCounts;
    private List<ShortMeetings> meetings;
}

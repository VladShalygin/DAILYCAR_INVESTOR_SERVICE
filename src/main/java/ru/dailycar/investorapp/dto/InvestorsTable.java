package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;
import ru.dailycar.investorapp.entities.DocumentPhotoStatus;
import ru.dailycar.investorapp.entities.InvestorType;

@Data
@Builder
public class InvestorsTable {
    private DocumentPhotoStatus documentStatus;
    private TableBidStatus bidStatus;
    private int activeContractCount;
    private String phoneNumber;
    private String surnameWithInitials;
    private long birthday;
    private InvestorType investorType;
}

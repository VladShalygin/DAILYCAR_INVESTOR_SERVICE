package ru.dailycar.investorapp.services;

import ru.dailycar.investorapp.dto.CreatePledgeDto;
import ru.dailycar.investorapp.dto.UpdatePledgeDto;
import ru.dailycar.investorapp.entities.PledgeEntity;
import ru.dailycar.investorapp.entities.PledgeType;

import java.util.List;

public interface PledgeService {

    public PledgeEntity getPledgeById(String id);

    public List<PledgeEntity> getPledgesByInvestorId(String userId);

    public PledgeEntity createPledge(CreatePledgeDto pledgeDto);
    public PledgeEntity updatePledge(UpdatePledgeDto pledgeDto, String sid);

    List<String> getPhotos(String sid);
}

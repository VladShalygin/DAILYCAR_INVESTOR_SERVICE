package ru.dailycar.investorapp.services;


import ru.dailycar.investorapp.dto.CreateAgentPercentageDTO;
import ru.dailycar.investorapp.dto.UpdateAgentPercentageDTO;
import ru.dailycar.investorapp.entities.AgentPercentage;

import java.util.List;

public interface AgentPercentageService {

    AgentPercentage getPercentageById(String id);

    List<AgentPercentage> getPercentages();

    AgentPercentage createPercentage(CreateAgentPercentageDTO percentageDto);

    AgentPercentage updatePercentage(UpdateAgentPercentageDTO percentageDto, String id);
}

package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreateAgentPercentageDTO;
import ru.dailycar.investorapp.dto.UpdateAgentPercentageDTO;
import ru.dailycar.investorapp.entities.AgentPercentage;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.AgentPercentageRepository;
import ru.dailycar.investorapp.services.AgentPercentageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentPercentageServiceImpl implements AgentPercentageService {

    private final ModelMapper mapper;
    private final AgentPercentageRepository repository;

    @Override
    public AgentPercentage getPercentageById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Процентов с таким айди не существует!"));
    }

    @Override
    public List<AgentPercentage> getPercentages() {
        return repository.findAll();
    }

    @Override
    public AgentPercentage createPercentage(CreateAgentPercentageDTO percentageDto) {
        return repository.save(mapper.map(percentageDto, AgentPercentage.class));
    }

    @Override
    public AgentPercentage updatePercentage(UpdateAgentPercentageDTO percentageDto, String id) {
        AgentPercentage existedPercentage = getPercentageById(id);
        mapper.map(percentageDto, existedPercentage);
        return repository.save(existedPercentage);
    }
}

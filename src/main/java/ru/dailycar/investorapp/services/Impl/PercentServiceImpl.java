package ru.dailycar.investorapp.services.Impl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreatePercentDto;
import ru.dailycar.investorapp.dto.UpdatePercentDto;
import ru.dailycar.investorapp.entities.Percent;
import ru.dailycar.investorapp.entities.PercentStatus;
import ru.dailycar.investorapp.entities.PercentType;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.PercentRepository;
import ru.dailycar.investorapp.services.PercentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PercentServiceImpl implements PercentService {

    private final PercentRepository repository;

    private final ModelMapper mapper;

    @Override
    public Percent getPercentById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Процент не найден!"));
    }

    @Override
    public List<Percent> getPercentByContractId(String contractId) {
        return repository.findByContractId(contractId);
    }

    @Override
    public List<Percent> getAllPercents() {
        return repository.findAll();
    }

    @Override
    public Percent updatePercent(UpdatePercentDto percent, String id) {
        Percent existedPercent = getPercentById(id);
        mapper.map(percent, existedPercent);
        return repository.save(existedPercent);
    }

    @Override
    public Percent createPercent(CreatePercentDto percent, String id) {
        return repository.save(
                Percent
                        .builder()
                        .amount(percent.getAmount())
                        .contractId(percent.getContractId())
                        .type(PercentType.valueOf(percent.getType()))
                        .timestamp(System.currentTimeMillis())
                        .build()
        );
    }
}

package ru.dailycar.investorapp.services;

import ru.dailycar.investorapp.dto.CreatePercentDto;
import ru.dailycar.investorapp.dto.UpdatePercentDto;
import ru.dailycar.investorapp.entities.Percent;

import java.util.List;

public interface PercentService {

    public Percent getPercentById(String id);

    public List<Percent> getPercentByContractId(String contractId);

    public List<Percent> getAllPercents();

    public Percent updatePercent(UpdatePercentDto percent, String id);

    public Percent createPercent(CreatePercentDto percent);

    Percent getPercentForCalculate(int number, String contractId);
}

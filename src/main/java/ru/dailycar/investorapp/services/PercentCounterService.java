package ru.dailycar.investorapp.services;

import ru.dailycar.investorapp.entities.Percent;

import java.util.List;

public interface PercentCounterService {

    void countPercent(String contractId);

    public List<Percent> predictOneMonth(String contractId);

}

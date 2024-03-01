package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.services.ContractService;
import ru.dailycar.investorapp.services.PercentCounterService;
import ru.dailycar.investorapp.services.SchedulerService;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final ContractService contractService;

    private final PercentCounterService basicPercentCounter;

    @Override
    @Scheduled(cron = "${scheduler.basePercent}")
    public void countBasicPercent() {
        System.out.println("start schedule");
        contractService.getActiveContracts().forEach(contract -> basicPercentCounter.countPercent(contract.getId()));
    }

    @Override
    public void countAgentPercent() {

    }
}

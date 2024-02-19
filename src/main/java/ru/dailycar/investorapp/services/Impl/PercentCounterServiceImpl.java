package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.entities.Contract;
import ru.dailycar.investorapp.entities.CountPeriod;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.services.ContractService;
import ru.dailycar.investorapp.services.PercentCounterService;
import ru.dailycar.investorapp.services.PercentService;
import ru.dailycar.investorapp.services.TransactionService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PercentCounterServiceImpl implements PercentCounterService {

    private final TransactionService transactionService;
    private final PercentService percentService;
    private final ContractService contractService;

    public void countBasicPercent(String contractId) {
        Contract contract = contractService.getContractById(contractId);
        if (contract == null) throw new NotFoundException("Договор не найден!");

        ArrayList<CountPeriod> periods = getCountPeriods(contract.getDateCreate(), contract.getDateExpiration());

        periods.forEach(period -> {

            Double amount = countAmount(period.getPercent(), period.getBalance(), period.getDaysInMonth(), period.getDaysInYear());



        });



    }

    private Double countAmount(Integer percent, Integer balance, Integer daysInMonth, Integer daysInYear) {
        return null;
    }

    private ArrayList<CountPeriod> getCountPeriods(Long dateCreate, Long dateExpiration) {
        return null;
    }


}

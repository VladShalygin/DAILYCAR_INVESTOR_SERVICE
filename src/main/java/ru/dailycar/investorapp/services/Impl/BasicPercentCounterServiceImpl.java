package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreatePercentDto;
import ru.dailycar.investorapp.dto.UpdatePercentDto;
import ru.dailycar.investorapp.entities.*;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.services.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class BasicPercentCounterServiceImpl implements PercentCounterService {

    private final TransactionService transactionService;
    private final PercentService percentService;
    private final ContractService contractService;

    private final AgentPercentageService agentPercentageService;

    ZoneId zone = ZoneId.of("Europe/Samara");
    ZoneOffset zoneOffset = ZoneOffset.of("+04:00");


    /*
     * Расчет процентов по инвестициям
     * */
    public void countPercent(String contractId) {
        try {
            Contract contract = contractService.getContractById(contractId);
            System.out.println(contract);
            if (contract == null) throw new NotFoundException("Договор не найден!");
            System.out.println("====================================");
            System.out.println(contract.getId());
            System.out.println(contract.getParentContractId());
            System.out.println("====================================");

            Contract agentContractFirstLevel = contract.getParentContractId() != null ? contractService.getContractById(contract.getParentContractId()) : null;
            AgentPercentage agentPercentageFirstLevel = agentContractFirstLevel != null ? agentPercentageService.getPercentageById(agentContractFirstLevel.getAgentPercentageId()) : null;
            System.out.println(agentContractFirstLevel);
            System.out.println(agentPercentageFirstLevel);

            AtomicInteger periodNumber = new AtomicInteger(0);
            List<Percent> percents = new ArrayList<>();

            Long dateEnd = contract.getDateExpiration();

            if (LocalDateTime.ofInstant(Instant.ofEpochMilli(dateEnd), zone).isAfter(LocalDateTime.now(zone))) {
                dateEnd = System.currentTimeMillis();
            }

            List<CountPeriod> periods = getCountPeriods(contract.getDateCreate(), dateEnd, contract.getPercent(), contract.getId());

            if (periods.isEmpty()) {
                return;
            }
            periods.forEach(period -> {
                periodNumber.getAndIncrement();
                Double amount = countAmount(period.getPercent(), period.getBalance(), period.getDaysInMonth(), period.getDaysInYear());

                Percent existedPercent = percentService.getPercentForCalculate(periodNumber.get(), contractId);
                addPercent(contractId, periodNumber.get(), period.getDateStart(), amount, existedPercent, PercentType.CONTRACT);
                if (agentContractFirstLevel != null && agentPercentageFirstLevel != null) {
                    Percent agentExistedPercent = percentService.getPercentForCalculate(periodNumber.get(), agentContractFirstLevel.getId());
                    Double agentAmount = countAmount(agentPercentageFirstLevel.getFirstLvl(), period.getBalance(), period.getDaysInMonth(), period.getDaysInYear());
                    addPercent(contract.getParentContractId(), periodNumber.get(), period.getDateStart(), agentAmount, agentExistedPercent, PercentType.AGENT);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void addPercent(String contractId, Integer periodNumber, Long dateStart, Double amount, Percent existedPercent, PercentType type) {
        if (existedPercent == null) {
            Percent percent = percentService.createPercent(
                    CreatePercentDto
                            .builder()
                            .amount(amount)
                            .contractId(contractId)
                            .type(type.toString())
                            .number(periodNumber)
                            .date(dateStart)
                            .build());
        } else {
            if (!existedPercent.getAmount().equals(amount)) {
                System.out.println("Изменился процент по договору " + contractId +
                        "\nСумма была: " + existedPercent.getAmount().toString() +
                        "\nСуума стала: " + amount);
                Percent percent = percentService.updatePercent(
                        UpdatePercentDto
                                .builder()
                                .amount(amount)
                                .timestamp(dateStart)
                                .build(),
                        existedPercent.getId());
            }
        }
    }

    private Double countAmount(Integer percent, Integer balance, Long daysInMonth, Long daysInYear) {
        return Math.round((balance * percent * daysInMonth / 100.0 / daysInYear) * 100.0) / 100.0;
    }

    private List<CountPeriod> getCountPeriods(Long timestampStart, Long timestampEnd, Integer percent, String contractId) {
        List<CountPeriod> periods = new ArrayList<>();


        LocalDateTime dateStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampStart), zone);
        LocalDateTime dateIteration = dateStart.plusMonths(1);
        LocalDateTime dateEnd = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampEnd), zone);

        LocalDateTime today = LocalDateTime.now(zone);

        if (dateEnd.isAfter(today)) {
            dateEnd = today;
        }

        long daysInYear = ChronoUnit.DAYS.between(dateStart, dateStart.plusYears(1));
        while (dateIteration.isBefore(dateEnd)) {
            periods.add(
                    CountPeriod
                            .builder()
                            .balance(getBalance(contractId, dateIteration.toEpochSecond(zoneOffset) * 1000))
                            .dateStart(dateIteration.toEpochSecond(zoneOffset) * 1000)
                            .dateEnd(dateIteration.plusMonths(1).toEpochSecond(zoneOffset) * 1000)
                            .percent(percent)
                            .daysInMonth(ChronoUnit.DAYS.between(dateIteration.minusMonths(1), dateIteration))
                            .daysInYear(daysInYear)
                            .build());
            dateIteration = dateIteration.plusMonths(1);
        }
        System.out.println(periods);
        return periods;
    }

    private Integer getBalance(String contractId, long dateEnd) {
        AtomicInteger sum = new AtomicInteger(0);
        transactionService.getInvestmentsBeforeDate(contractId, dateEnd).forEach(transaction -> {
            sum.addAndGet(transaction.getAmount());
        });
        return sum.get();
    }

}

package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreatePercentDto;
import ru.dailycar.investorapp.dto.UpdatePercentDto;
import ru.dailycar.investorapp.entities.*;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.services.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class BasicPercentCounterServiceImpl implements PercentCounterService {

    private final TransactionService transactionService;
    private final PercentService percentService;
    private final ContractService contractService;

    private final AgentPercentageService agentPercentageService;

    ZoneId zone = ZoneId.of("Europe/Moscow");
    ZoneOffset zoneOffset = ZoneOffset.of("+03:00");


    /*
     * Расчет процентов по инвестициям
     * */
    public void countPercent(String contractId) {
        try {
            Contract contract = contractService.getContractById(contractId);
            System.out.println(contract);
            if (contract == null) throw new NotFoundException("Договор не найден!");

            Contract agentContractFirstLevel = contract.getParentContractId() != null && !contract.getParentContractId().equals("empty") ? contractService.getContractById(contract.getParentContractId()) : null;
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
                addPercent(contractId, periodNumber.get(), period.getDateEnd(), amount, existedPercent, PercentType.CONTRACT, null);
                if (agentContractFirstLevel != null && agentPercentageFirstLevel != null) {
                    Percent agentExistedPercent = percentService.getAgentPercentForCalculate(periodNumber.get(), agentContractFirstLevel.getId(), contract.getId());
                    Double agentAmount = countAmount(agentPercentageFirstLevel.getFirstLvl(), period.getBalance(), period.getDaysInMonth(), period.getDaysInYear());
                    addPercent(contract.getParentContractId(), periodNumber.get(), period.getDateStart(), agentAmount, agentExistedPercent, PercentType.AGENT, contractId);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void addPercent(String contractId, int periodNumber, Long dateStart, Double amount, Percent existedPercent, PercentType type, String invitedInvestorContract) {
        boolean isNeedInvitedContract = type.equals(PercentType.AGENT) && !invitedInvestorContract.isEmpty();
        System.out.println("--------------------------------");
        System.out.println(existedPercent);

        if (existedPercent == null) {
            CreatePercentDto dto =
                    CreatePercentDto
                            .builder()
                            .amount(amount)
                            .contractId(contractId)
                            .type(type.toString())
                            .number(periodNumber)
                            .date(dateStart)
                            .build();
            if (isNeedInvitedContract)
                dto.setInvitedInvestorContractId(invitedInvestorContract);
            Percent percent = percentService.createPercent(dto);
        } else {
            if (!existedPercent.getAmount().equals(amount)) {
                System.out.println("Изменился процент по договору " + contractId +
                        "\nСумма была: " + existedPercent.getAmount().toString() +
                        "\nСуума стала: " + amount);
                UpdatePercentDto dto =
                        UpdatePercentDto
                                .builder()
                                .amount(amount)
                                .timestamp(dateStart)
                                .build();
                if (isNeedInvitedContract)
                    dto.setInvitedInvestorContractId(invitedInvestorContract);
                Percent percent = percentService.updatePercent(
                        dto,
                        existedPercent.getId());
            }
        }
    }

    private Double countAmount(int percent, int balance, Long daysInMonth, Long daysInYear) {
        return Math.round((balance * percent * daysInMonth / 100.0 / daysInYear) * 100.0) / 100.0;
    }

    private List<CountPeriod> getCountPeriods(Long timestampStart, Long timestampEnd, int percent, String contractId) {
        List<CountPeriod> periods = new ArrayList<>();

        LocalDateTime dateStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampStart), zone).plusDays(1);
        LocalDateTime dateIteration = dateStart.plusMonths(1);
        LocalDateTime dateEnd = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampEnd), zone).plusDays(1);

        long daysInYear = ChronoUnit.DAYS.between(dateStart, dateStart.plusYears(1));
        System.out.println("iteration dates:");
        System.out.println(dateIteration);
        System.out.println(dateEnd);
        while ((dateIteration.isBefore(dateEnd) || dateIteration.isEqual(dateEnd))) {
            periods.add(
                    CountPeriod
                            .builder()
                            .balance(getBalance(contractId, dateIteration.toEpochSecond(zoneOffset) * 1000))
                            .dateStart(dateIteration.minusMonths(1).toEpochSecond(zoneOffset) * 1000)
                            .dateEnd(dateIteration.toEpochSecond(zoneOffset) * 1000)
                            .percent(percent)
                            .daysInMonth(ChronoUnit.DAYS.between(dateIteration.minusMonths(1), dateIteration))
                            .daysInYear(daysInYear)
                            .build());
            dateIteration = dateIteration.plusMonths(1);
        }
        System.out.println("Periods = ");
        System.out.println(periods);
        return periods;
    }

    private int getBalance(String contractId, long dateEnd) {
        AtomicInteger sum = new AtomicInteger(0);
        transactionService.getInvestmentsBeforeDate(contractId, dateEnd).forEach(transaction -> {
            sum.addAndGet(transaction.getAmount());
        });
        return sum.get();
    }

    private Percent countPercentForPredict(long dateCreate, long dateExpiration, int percent, String contractId, PercentType type, String agentContractId) {
        System.out.println("contractId");
        System.out.println(contractId);
        LocalDateTime dateStartContract = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateCreate), zone).plusDays(1);
        LocalDateTime dateEndContract = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateExpiration), zone).plusDays(1);

        if (dateEndContract.isAfter(LocalDateTime.now(zone))) {
            dateEndContract = LocalDateTime.now(zone).plusDays(1);
        }
        System.out.println("dates: ");
        System.out.println(dateEndContract);
        System.out.println(dateStartContract);

        //todo добавить проверку что если вышли за предел действия контракта выкидывать ошибку
        long timestampStartPeriod = 0;
        long timestampEndPeriod = 0;
        int monthDifference = (int) ChronoUnit.MONTHS.between(dateStartContract, dateEndContract);
        System.out.println("monthDifference =");
        System.out.println(monthDifference);
        if (monthDifference < 1) {
            timestampStartPeriod = dateStartContract.plusDays(1).toInstant(zoneOffset).toEpochMilli();
            timestampEndPeriod = dateStartContract.plusMonths(1).toInstant(zoneOffset).toEpochMilli();
            monthDifference = 1;
        } else {
            System.out.println("dates in predict");
            System.out.println(dateStartContract.plusMonths(monthDifference));
            System.out.println(dateStartContract.plusMonths(monthDifference + 1));
            timestampStartPeriod = dateStartContract.plusMonths(monthDifference).toInstant(zoneOffset).toEpochMilli();
            timestampEndPeriod = dateStartContract.plusMonths(monthDifference + 1).toInstant(zoneOffset).toEpochMilli();
        }
        List<CountPeriod> periods = getCountPeriods(timestampStartPeriod, timestampEndPeriod, percent, contractId);
        if (periods.isEmpty()) {
            return null;
        }
        CountPeriod period = periods.get(0);
        System.out.println(period);
        Double amount = countAmount(period.getPercent(), period.getBalance(), period.getDaysInMonth(), period.getDaysInYear());

        Percent userPercent = Percent
                .builder()
                .id(UUID.randomUUID().toString())
                .contractId(contractId)
                .number(monthDifference)
                .type(type)
                .timestamp(timestampEndPeriod)
                .amount(amount)
                .build();

        if(type.equals(PercentType.AGENT) && !agentContractId.isEmpty()) {
            userPercent.setContractId(agentContractId);
            userPercent.setInvitedInvestorContractId(contractId);
        }

        return userPercent;
    }

    @Override
    public List<Percent> predictOneMonth(String userId) {
        try {
            List<Contract> userContracts = contractService.getContractsByUserId(userId);
            if (userContracts == null || userContracts.isEmpty()) {
                throw new NotFoundException("Договоры не найдены или не существуют!");
            }

            List<Percent> percents = new ArrayList<Percent>();

            for (Contract contract : userContracts) {
                if (contract.getType().equals(ContractType.AGENT)) {
                    if (StringUtils.equals(contract.getAgentPercentageId(), "empty")) {
                        continue;
                    }
                    AgentPercentage percentage = agentPercentageService.getPercentageById(contract.getAgentPercentageId());
                    System.out.println("percentage = ");
                    System.out.println(percentage);
                    contractService.getContractsByAgentContractId(contract.getId()).forEach(parentContract -> {
                        Percent percent = countPercentForPredict(parentContract.getDateCreate(), parentContract.getDateExpiration(), percentage.getFirstLvl(), parentContract.getId(), PercentType.AGENT, contract.getId());
                        if (percent != null) {
                            percents.add(percent);
                        }
                    });
                } else {
                    Percent percent = countPercentForPredict(contract.getDateCreate(), contract.getDateExpiration(), contract.getPercent(), contract.getId(), PercentType.CONTRACT, contract.getId());
                    if (percent != null) {
                        percents.add(percent);
                    }
                }
            }
            ;
            System.out.println(percents);
            return percents;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

}

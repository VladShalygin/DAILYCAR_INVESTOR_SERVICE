package ru.dailycar.investorapp.services.Impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreateTransactionDTO;
import ru.dailycar.investorapp.dto.UpdateTransactionDTO;
import ru.dailycar.investorapp.entities.*;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.TransactionRepository;
import ru.dailycar.investorapp.services.PercentService;
import ru.dailycar.investorapp.services.TransactionService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final ModelMapper mapper;
    private final TransactionRepository repository;

    private final PercentService percentService;

    @Override
    public Transaction getTransactionById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Данная транзацкция не существует!"));
    }

    @Override
    public List<Transaction> getTransactionsByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Transaction> getInvestmentsByContractId(String contractId) {
        return repository.findContractInvestments(contractId);
    }

    @Override
    public List<Transaction> getTransactionsByContractId(String contractId) {
        return repository.findTransactionsByContractId(contractId);
    }

    @Override
    public Transaction createTransaction(@Valid CreateTransactionDTO createTransactionDTO) throws BadRequestException {
        if (createTransactionDTO.getType().equals(ContractType.AGENT)) {
            List<Percent> percents = percentService.getPercentByContractId(createTransactionDTO.getContractId());
            List<Transaction> payoutTransactions = getInvestmentsByContractId(createTransactionDTO.getContractId()).stream().filter(transaction -> transaction.getType().equals(TransactionType.PAYOUT)).toList();
            if (percents.isEmpty()) throw new BadRequestException("Нет суммы для вывода!");
            double countPercentAmount = 0;
            double countTransactionAmount = 0;
            for (Percent percent : percents) {
                countPercentAmount += percent.getAmount();
            }
            for (Transaction transaction : payoutTransactions) {
                countTransactionAmount += transaction.getAmount();
            }
            if (countTransactionAmount <= countPercentAmount) throw new BadRequestException("Недостаточно средств!");
        }
        return repository.save(
                Transaction.builder()
                        .userId(createTransactionDTO.getUserId())
                        .amount(createTransactionDTO.getAmount())
                        .contractId(createTransactionDTO.getContractId())
                        .date(System.currentTimeMillis())
                        .status(TransactionStatus.CREATED)
                        .type(createTransactionDTO.getType())
                        .requisiteId(createTransactionDTO.getRequisiteId())
                        .build());
    }

    @Override
    public Transaction updateTransaction(UpdateTransactionDTO updateTransactionDTO, @Valid @NotBlank String id) {
        Transaction existedTransaction = repository.findById(id).orElseThrow(() -> new NotFoundException("Данная транзацкция не существует"));
        mapper.map(updateTransactionDTO, existedTransaction);
        return repository.save(existedTransaction);
    }

    @Override
    public List<Transaction> getInvestmentsBeforeDate(String contractId, Long dateEnd) {
        return repository.findInvestmentsBeforeDate(contractId, dateEnd);
    }


}

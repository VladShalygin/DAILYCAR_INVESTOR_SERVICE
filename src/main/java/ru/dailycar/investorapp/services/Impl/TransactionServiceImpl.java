package ru.dailycar.investorapp.services.Impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreateTransactionDTO;
import ru.dailycar.investorapp.dto.UpdateTransactionDTO;
import ru.dailycar.investorapp.entities.Transaction;
import ru.dailycar.investorapp.entities.TransactionStatus;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.TransactionRepository;
import ru.dailycar.investorapp.services.TransactionService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final ModelMapper mapper;
    private final TransactionRepository repository;

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
    public Transaction createTransaction(@Valid CreateTransactionDTO createTransactionDTO) {
        return repository.save(
                Transaction.builder()
                        .userId(createTransactionDTO.getUserId())
                        .amount(createTransactionDTO.getAmount())
                        .contractId(createTransactionDTO.getContractId())
                        .date(System.currentTimeMillis())
                        .status(TransactionStatus.CREATED)
                        .type(createTransactionDTO.getType())
                        .percentId(createTransactionDTO.getPercentId())
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

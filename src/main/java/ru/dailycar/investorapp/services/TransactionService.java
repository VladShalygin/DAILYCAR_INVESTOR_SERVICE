package ru.dailycar.investorapp.services;

import ru.dailycar.investorapp.dto.CreateTransactionDTO;
import ru.dailycar.investorapp.dto.UpdateTransactionDTO;
import ru.dailycar.investorapp.entities.Transaction;
import ru.dailycar.investorapp.entities.TransactionType;

import java.util.List;

public interface TransactionService {
    public Transaction getTransactionById(String id);

    public List<Transaction> getTransactionsByUserId(String userId);

    List<Transaction> getInvestmentsByContractId(String contractId);

    public Transaction createTransaction(CreateTransactionDTO createTransactionDTO);

    public Transaction updateTransaction(UpdateTransactionDTO createTransactionDTO, String id);

    public List<Transaction> getInvestmentsBeforeDate(String contractId, Long dateEnd);
}

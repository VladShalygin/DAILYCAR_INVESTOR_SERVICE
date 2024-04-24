package ru.dailycar.investorapp.services;

import org.apache.coyote.BadRequestException;
import ru.dailycar.investorapp.dto.CreateTransactionDTO;
import ru.dailycar.investorapp.dto.UpdateTransactionDTO;
import ru.dailycar.investorapp.entities.Transaction;
import ru.dailycar.investorapp.entities.TransactionType;

import java.util.List;

public interface TransactionService {
    public Transaction getTransactionById(String id);

    public List<Transaction> getTransactionsByUserId(String userId);

    List<Transaction> getInvestmentsByContractId(String contractId);

    List<Transaction> getTransactionsByContractId(String contractId);

    public Transaction createTransaction(CreateTransactionDTO createTransactionDTO) throws BadRequestException;

    public Transaction updateTransaction(UpdateTransactionDTO createTransactionDTO, String id);

    public List<Transaction> getInvestmentsBeforeDate(String contractId, Long dateEnd);

}

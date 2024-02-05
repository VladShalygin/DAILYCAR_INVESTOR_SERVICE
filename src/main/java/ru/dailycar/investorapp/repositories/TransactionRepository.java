package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);

}

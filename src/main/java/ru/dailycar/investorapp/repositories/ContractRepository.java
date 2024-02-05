package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.Contract;

import java.util.List;

@Repository
public interface ContractRepository extends MongoRepository<Contract, String> {
    List<Contract> findByUserId(String userId);
}

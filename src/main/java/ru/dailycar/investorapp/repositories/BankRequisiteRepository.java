package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.BankRequisite;

import java.util.List;

@Repository
public interface BankRequisiteRepository extends MongoRepository<BankRequisite, String> {
    List<BankRequisite> findByUserId(String userId);
}

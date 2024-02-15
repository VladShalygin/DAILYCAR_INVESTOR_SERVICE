package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.Percent;

import java.util.List;

@Repository
public interface PercentRepository  extends MongoRepository<Percent, String> {
    List<Percent> findByContractId(String contractId);

}

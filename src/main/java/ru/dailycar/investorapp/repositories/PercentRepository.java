package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.Percent;

import java.util.List;

@Repository
public interface PercentRepository  extends MongoRepository<Percent, String> {
    List<Percent> findByContractId(String contractId);

    @Query(value = "{'number': ?0, 'contractId': ?1}")
    Percent findByNumberAndContractId(int number, String contractId);

    @Query(value = "{'number': ?0, 'contractId': ?1, 'invitedInvestorContractId': ?2}")
    Percent findAgentPercent(int number, String contractId, String invitedInvestorContractId);
}

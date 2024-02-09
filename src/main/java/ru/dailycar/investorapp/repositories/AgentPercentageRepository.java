package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.AgentPercentage;

@Repository
public interface AgentPercentageRepository extends MongoRepository<AgentPercentage, String> {
}

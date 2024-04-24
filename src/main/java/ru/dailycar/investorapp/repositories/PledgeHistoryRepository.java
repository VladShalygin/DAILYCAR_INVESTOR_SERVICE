package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.PledgeHistory;

@Repository
public interface PledgeHistoryRepository extends MongoRepository<PledgeHistory, String> {
}

package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.PledgeEntity;

@Repository
public interface PledgeRepository extends MongoRepository<PledgeEntity, String> {
    PledgeEntity findBySid(String sid);
}

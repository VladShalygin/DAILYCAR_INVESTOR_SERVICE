package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.PledgeEntity;
import ru.dailycar.investorapp.entities.PledgeStatus;

@Repository
public interface PledgeRepository extends MongoRepository<PledgeEntity, String> {
    PledgeEntity findBySid(String sid);

    @Query(value = "{'status' : ?0}", count = true)
    int countByStatus(String pledgeStatus);
}

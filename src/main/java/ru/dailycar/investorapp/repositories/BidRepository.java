package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.Bid;

import java.util.List;

@Repository
public interface BidRepository extends MongoRepository<Bid, String> {
    List<Bid> findByUserId(String userId);
}

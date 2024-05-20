package ru.dailycar.investorapp.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.Meeting;

import java.util.List;

@Repository
public interface MeetingRepository extends MongoRepository<Meeting, String> {
    List<Meeting> findByUserId(String userId);
    List<Meeting> findByDateGreaterThanEqualOrderByDateAsc(Long currentTime, Pageable pageable);
}

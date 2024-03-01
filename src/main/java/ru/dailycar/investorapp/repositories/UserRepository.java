package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{$or: [{email: {$eq: ?0}}, {phoneNumber: {$eq: ?0}}]}")
    Optional<User> findByEmailOrPhoneNumber(String username);

    boolean existsByPhoneNumber(String normalizedPhoneNumber);

    boolean existsByEmail(String email);

}

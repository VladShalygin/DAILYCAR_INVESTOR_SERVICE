package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.DocumentPhoto;

import java.util.List;

@Repository
public interface DocumentPhotoRepository extends MongoRepository<DocumentPhoto, String> {
    List<DocumentPhoto> findByUserId(String userId);
}

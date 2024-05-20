package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.entities.DocumentPhoto;
import ru.dailycar.investorapp.entities.DocumentPhotoType;

import java.util.List;

@Repository
public interface DocumentPhotoRepository extends MongoRepository<DocumentPhoto, String> {
    List<DocumentPhoto> findByUserId(String userId);

    @Query(value = "{'userId': ?0, 'type':  ?1}")
    List<DocumentPhoto> findDocumentsByUserId(String userId, DocumentPhotoType documentPhotoType);
}

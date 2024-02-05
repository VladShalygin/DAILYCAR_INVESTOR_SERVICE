package ru.dailycar.investorapp.services;

import org.springframework.web.multipart.MultipartFile;
import ru.dailycar.investorapp.entities.DocumentPhoto;

import java.util.List;

public interface DocumentPhotoService {

    public List<DocumentPhoto> getDocumentPhotos();

    public DocumentPhoto getDocumentPhotoById(String id);

    public List<DocumentPhoto> getDocumentPhotosByUserId(String userId);

    public DocumentPhoto uploadDocumentsPhoto(MultipartFile file, String userId);

}

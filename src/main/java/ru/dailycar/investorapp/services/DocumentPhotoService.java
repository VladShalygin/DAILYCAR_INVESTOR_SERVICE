package ru.dailycar.investorapp.services;

import org.springframework.web.multipart.MultipartFile;
import ru.dailycar.investorapp.entities.DocumentPhoto;

import java.util.List;

public interface DocumentPhotoService {

    public List<DocumentPhoto> getDocumentPhotos();

    public DocumentPhoto getDocumentPhotoById(String id);

    public List<DocumentPhoto> getDocumentPhotosByUserId(String userId);

    List<DocumentPhoto> getDocumentPhotosByContractId(String contractId);

    public DocumentPhoto uploadDocumentsPhoto(MultipartFile file, String userId);

    public DocumentPhoto updateDocumentsPhoto(MultipartFile file, String id);

    public DocumentPhoto denyDocumentPhoto(String id);

    public DocumentPhoto acceptDocumentPhoto(String id);

    public DocumentPhoto uploadContractsPhoto(MultipartFile file, String userId, String contractId);
}

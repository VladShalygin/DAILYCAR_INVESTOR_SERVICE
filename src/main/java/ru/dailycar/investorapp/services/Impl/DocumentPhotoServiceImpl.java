package ru.dailycar.investorapp.services.Impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dailycar.investorapp.entities.DocumentPhoto;
import ru.dailycar.investorapp.entities.DocumentPhotoStatus;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.DocumentPhotoRepository;
import ru.dailycar.investorapp.services.DocumentPhotoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@Service
public class DocumentPhotoServiceImpl implements DocumentPhotoService {

    @Value("${DOCS_UPLOAD_DIR}")
    private String UPLOAD_DIR;

    private final DocumentPhotoRepository repository;

    @Override
    public List<DocumentPhoto> getDocumentPhotos() {
        return repository.findAll();
    }

    @Override
    public DocumentPhoto getDocumentPhotoById(@Valid @NotBlank String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Не найден документ с фото"));
    }

    @Override
    public List<DocumentPhoto> getDocumentPhotosByUserId(@Valid @NotBlank String userId) {
        return repository.findByUserId(userId);
    }


    @Override
    public DocumentPhoto uploadDocumentsPhoto(MultipartFile file, @Valid @NotBlank String userId) {

        try {
            String filename = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            Path filepath = Paths.get(UPLOAD_DIR, filename);
            Files.createDirectories(filepath.getParent());
            Files.write(filepath, file.getBytes());

            DocumentPhoto photo = DocumentPhoto.builder()
                    .userId(userId)
                    .path("/docs/" + filename)
                    .dateUpload(System.currentTimeMillis())
                    .status(DocumentPhotoStatus.NEW)
                    .build();

            return repository.save(photo);


        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения файла: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public DocumentPhoto updateDocumentsPhoto(MultipartFile file, String id) {

        try {
            DocumentPhoto existedPhoto = getDocumentPhotoById(id);

            String filename = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            Path filepath = Paths.get(UPLOAD_DIR, filename);
            Files.createDirectories(filepath.getParent());
            Files.write(filepath, file.getBytes());

            existedPhoto.setStatus(DocumentPhotoStatus.NEW);
            existedPhoto.setPath("/docs/" + filename);

            return repository.save(existedPhoto);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения файла: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public DocumentPhoto denyDocumentPhoto(String id) {
        DocumentPhoto existedPhoto = getDocumentPhotoById(id);
        existedPhoto.setStatus(DocumentPhotoStatus.DENYIED);
        return repository.save(existedPhoto);
    }
    @Override
    public DocumentPhoto acceptDocumentPhoto(String id) {
        DocumentPhoto existedPhoto = getDocumentPhotoById(id);
        existedPhoto.setStatus(DocumentPhotoStatus.ACCEPTED);
        return repository.save(existedPhoto);
    }

}
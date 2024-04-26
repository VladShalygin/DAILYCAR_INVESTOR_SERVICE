package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletContext;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.dailycar.investorapp.entities.DocumentPhoto;
import ru.dailycar.investorapp.services.DocumentPhotoService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/investments/document-photo")
@RequiredArgsConstructor
@Tag(name = "Фото/pdf документов инвестора")
public class DocumentPhotoController {

    private final DocumentPhotoService service;
    private ServletContext servletContext;

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение всех фоток документов")
    public ResponseEntity<List<DocumentPhoto>> getDocumentPhotos() {
        return ResponseEntity.ok(service.getDocumentPhotos());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение фото документа инвестора по ID документа")
    public ResponseEntity<DocumentPhoto> getDocumentPhotoById(@PathVariable @Parameter(description = "ID документа") String id) {
        return ResponseEntity.ok(service.getDocumentPhotoById(id));
    }

    @GetMapping("/user/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение всех документов инвестора")
    public ResponseEntity<List<DocumentPhoto>> getDocumentPhotosByUserId(@PathVariable @Parameter(description = "ID инвестора") String userId) {
        return ResponseEntity.ok(service.getDocumentPhotosByUserId(userId));
    }

    @GetMapping("/contract/{contractId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение всех документов по id договора")
    public ResponseEntity<List<DocumentPhoto>> getDocumentPhotosByContractId(@PathVariable @Parameter(description = "ID контракта") String contractId) {
        return ResponseEntity.ok(service.getDocumentPhotosByContractId(contractId));
    }

    @PatchMapping("/accept/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Принятие документа инвестора")
    public ResponseEntity<DocumentPhoto> acceptDocument(@PathVariable @Parameter(description = "ID документа") String id) {
        return ResponseEntity.ok(service.acceptDocumentPhoto(id));
    }

    @PatchMapping("/deny/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Отклонение документа инвестора")
    public ResponseEntity<DocumentPhoto> denyDocument(@PathVariable @Parameter(description = "ID документа") String id) {
        return ResponseEntity.ok(service.denyDocumentPhoto(id));
    }

    //    @PostMapping("/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "JWT")
    @RequestMapping(value = "/docs/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Выгрузка фоток/pdf user_docs на сервер и их сохранение")
    public ResponseEntity<DocumentPhoto> uploadDocumentsPhoto(
            @RequestBody @Parameter(description = "Файл") MultipartFile file,
            @RequestParam("userId") @Parameter(description = "ID инвестора") String userId) {
        return ResponseEntity.ok(service.uploadDocumentsPhoto(file, userId));
    }

    @SecurityRequirement(name = "JWT")
    @RequestMapping(value = "/contracts/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Выгрузка фоток/pdf contracts на сервер и их сохранение")
    public ResponseEntity<DocumentPhoto> uploadContractsPhoto(
            @RequestBody @Parameter(description = "Файл") MultipartFile file,
            @RequestParam("contractId") @Parameter(description = "ID договра") String contractId,
            @RequestParam("userId") @Parameter(description = "ID инвестора") String userId) {
        return ResponseEntity.ok(service.uploadContractsPhoto(file, userId, contractId));
    }

    @SecurityRequirement(name = "JWT")
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление фоток/pdf на сервер и их сохранение")
    public ResponseEntity<DocumentPhoto> updateDocumentsPhoto(
            @RequestBody @Parameter(description = "Файл") MultipartFile file,
            @RequestParam("id") @Parameter(description = "ID документа") String id) {
        return ResponseEntity.ok(service.updateDocumentsPhoto(file, id));
    }

    @GetMapping("/photo")
    public ResponseEntity<Resource> getPhoto(@RequestParam String filename) throws IOException {
//        Resource resource = new ClassPathResource(
//                 "resources/static/photos" + filename
//        );
        Resource resource = new FileSystemResource("/home/autocars/api.dailycar.ru/investor-app/resources/static/photos" + filename);

        HttpHeaders headers = new HttpHeaders();

        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else if (filename.endsWith(".png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if (filename.endsWith(".pdf")) {
            headers.setContentType(MediaType.APPLICATION_PDF);
        } else {
            // Handle unsupported file formats
            throw new IllegalArgumentException("Unsupported file format");
        }
        headers.setContentDispositionFormData("attachment", filename);

        return ResponseEntity.ok().headers(headers).body(resource);
    }


}
package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
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
@Tag(name="Фото/pdf документов инвестора")
public class DocumentPhotoController {

    private final DocumentPhotoService service;

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

//    @PostMapping("/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "JWT")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Выгрузка фоток/pdf на сервер и их сохранение")
    public ResponseEntity<DocumentPhoto> uploadDocumentsPhoto(
            @RequestBody @Parameter(description = "Массив файлов") MultipartFile file,
            @RequestParam("userId") @Parameter(description = "ID инвестора") String userId) {
        System.out.println(file);
        return ResponseEntity.ok(service.uploadDocumentsPhoto(file, userId));
    }

    @GetMapping("/photo")
    public ResponseEntity<Resource> getPhoto(@RequestParam String filename) throws IOException {
        System.out.println(filename);
        Resource resource = new FileSystemResource(
                "src/main/resources/static/photos" + filename
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(resource.contentLength());
        headers.setContentDispositionFormData("attachment", filename);

        // Вернуть объект Resource в ответе на запрос
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.dailycar.investorapp.dto.CreateContractDTO;
import ru.dailycar.investorapp.dto.UpdateContractDTO;
import ru.dailycar.investorapp.entities.Contract;
import ru.dailycar.investorapp.entities.DocumentPhoto;
import ru.dailycar.investorapp.services.ContractService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/investments/contract")
@Tag(name="Договоры", description = "Работа с договорами инвесторов")
public class ContractController {

    private final ContractService service;

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Список всех договор")
    public ResponseEntity<List<Contract>> getContracts() {
        return ResponseEntity.ok(service.getContracts());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение договора по ID")
    public ResponseEntity<Contract> getContractById(@PathVariable @Parameter(description = "ID заявки") String id) {
        return ResponseEntity.ok(service.getContractById(id));
    }

    @GetMapping("/investor/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение всех договоров инвестора")
    public ResponseEntity<List<Contract>> getContractsByUserId(@PathVariable @Parameter(description = "ID инвестора") String userId) {
        return ResponseEntity.ok(service.getContractsByUserId(userId));
    }

    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Добавленкие договора для инвестора")
    public ResponseEntity<Contract> createContract(@RequestBody @Parameter(description = "Объект для создания договора") CreateContractDTO createContractDTO) {
        return ResponseEntity.ok(service.createContract(createContractDTO));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление пользователя")
    public ResponseEntity<Contract> updateContract(@RequestBody @Parameter(description = "Объект для обновления договора") UpdateContractDTO updateContractDTO,
                                                   @PathVariable @Parameter(description = "ID обновляемого договра") String id) {
        return ResponseEntity.ok(service.updateContract(updateContractDTO, id));
    }

    @SecurityRequirement(name = "JWT")
    @CrossOrigin
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка контракта на сервер и их сохранение")
    public ResponseEntity<Contract> uploadContract(
            @RequestBody @Parameter(description = "Файл") MultipartFile file,
            @RequestParam("id") @Parameter(description = "ID контракта") String id) throws IOException {
        return ResponseEntity.ok(service.uploadContract(file, id));
    }

    @GetMapping("/file")
    @CrossOrigin
    public ResponseEntity<Resource> getPhoto(@RequestParam String filename) throws IOException {
        Resource resource = new FileSystemResource(
                "src/main/resources/static" + filename
        );
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.ALL);
        headers.setContentLength(resource.contentLength());
        headers.setContentDispositionFormData("attachment", filename);

        return ResponseEntity.ok().headers(headers).body(resource);
    }

}

package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.dailycar.investorapp.dto.CreateContractDTO;
import ru.dailycar.investorapp.dto.InvestorsNames;
import ru.dailycar.investorapp.dto.UpdateContractDTO;
import ru.dailycar.investorapp.entities.Contract;
import ru.dailycar.investorapp.entities.Percent;
import ru.dailycar.investorapp.services.ContractService;
import ru.dailycar.investorapp.services.PercentCounterService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/investments/contract")
@Tag(name = "Договоры", description = "Работа с договорами инвесторов")
public class ContractController {

    private final ContractService service;
    private final PercentCounterService percentCounterService;

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение всех договорв")
    public ResponseEntity<List<Contract>> getContracts() {
        return ResponseEntity.ok(service.getContracts());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "получение договора по ID")
    public ResponseEntity<Contract> getContractById(@PathVariable @Parameter(description = "ID договора") String id) {
        return ResponseEntity.ok(service.getContractById(id));
    }

    @GetMapping("/investor/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение договорв инвестора")
    public ResponseEntity<List<Contract>> getContractsByUserId(@PathVariable @Parameter(description = "ID инвестора") String userId) {
        return ResponseEntity.ok(service.getContractsByUserId(userId));
    }

    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создание договора")
    public ResponseEntity<Contract> createContract(@RequestBody @Parameter(description = "сущность создания договора") CreateContractDTO createContractDTO) {
        return ResponseEntity.ok(service.createContract(createContractDTO));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление договора")
    public ResponseEntity<Contract> updateContract(@RequestBody @Parameter(description = "Сущность для обновления") UpdateContractDTO updateContractDTO,
                                                   @PathVariable @Parameter(description = "ID договора") String id) {
        return ResponseEntity.ok(service.updateContract(updateContractDTO, id));
    }

    @SecurityRequirement(name = "JWT")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка контрактов на сервер и их сохранение")
    public ResponseEntity<Contract> uploadContract(
            @RequestBody @Parameter(description = "файл") MultipartFile file,
            @RequestParam("id") @Parameter(description = "ID загрузка договора") String id) throws IOException {
        return ResponseEntity.ok(service.uploadContract(file, id));
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> getPhoto(@RequestParam String filename) throws IOException {
        Resource resource = new FileSystemResource(
                "src/main/resources/static" + filename
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(resource.contentLength());
        headers.setContentDispositionFormData("attachment", filename);

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    //todo тестовый запрос удалить в проде
    @GetMapping("/test/{contractId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "????????? ???? ????????? ?????????")
    public ResponseEntity<String> testCount(@PathVariable @Parameter(description = "ID contract") String contractId) {
//        percentCounterService.countBasicContract(contractId);
        percentCounterService.countPercent(contractId);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/countNextMonth/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Расчет процентов за след месяц по всем договорам инвестора")
    public ResponseEntity<List<Percent>> countNextMonth(@PathVariable @Valid @NotBlank @Parameter(description = "Id инвесоора") String userId) {
        return ResponseEntity.ok(percentCounterService.predictOneMonth(userId));
    }

    @GetMapping("/getInvestorsName/{parentReferralCode}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение ФИО инвесторов по парент реферал Id ")
    public ResponseEntity<List<InvestorsNames>> getInvestorsByParentReferralCode(@PathVariable @Valid @NotBlank @Parameter(description = "Id инвесоора") String parentReferralCode,
                                                                                 @RequestHeader(name = "Authorization") String authorizationHeader) {
        return ResponseEntity.ok(service.getInvestorsNameByParentReferralCode(parentReferralCode, authorizationHeader));
    }

    @GetMapping("/changePledge")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Смена залогового имущества ")
    public ResponseEntity<Contract> changePledge(@RequestParam String contractId,
                                                 @RequestParam String newPledgeId,
                                                 @RequestHeader(name = "Authorization") String authorizationHeader,
                                                 @RequestHeader(name = "X-auth-user-id") String userId) {
        return ResponseEntity.ok(service.changePledge(contractId, newPledgeId, userId));
    }
}

package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.dto.CreateBankRequisitesDTO;
import ru.dailycar.investorapp.dto.UpdateBankRequisiteDTO;
import ru.dailycar.investorapp.entities.BankRequisite;
import ru.dailycar.investorapp.services.BankRequisiteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/investments/bankRequisite")
@Tag(name = "Банковские реквизиты", description = "получение, обновление и создание банковских реквизитов")
public class BankRequisiteController {

    private final BankRequisiteService service;

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Банковские реквизиты по id"
    )
    public ResponseEntity<BankRequisite> getBankRequisiteById(@RequestParam String id) {
        return ResponseEntity.ok(service.getBankRequisiteById(id));
    };

    @GetMapping("/investor")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение всех банковских реквизитов пользователя")
    ResponseEntity<List<BankRequisite>> getBankRequisiteByUserId(@RequestParam String userId) {
        return ResponseEntity.ok(service.getBankRequisiteByUserId(userId));
    }

    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создание банковских реквизитов")
    ResponseEntity<BankRequisite> createBankRequisite( @Valid @RequestBody CreateBankRequisitesDTO requisite) {
        return ResponseEntity.ok(service.createBankRequisite(requisite));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление банковсих реквизитов")
    ResponseEntity<BankRequisite> updateBankRequisite(@Valid @RequestBody UpdateBankRequisiteDTO requisite, @PathVariable String id) {
        return ResponseEntity.ok(service.updateBankRequisite(requisite, id));
    }


}

package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.dto.CreateTransactionDTO;
import ru.dailycar.investorapp.dto.UpdateTransactionDTO;
import ru.dailycar.investorapp.entities.Transaction;
import ru.dailycar.investorapp.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/investments/transaction")
@RequiredArgsConstructor
@Tag(name="Платежные транзакции", description = "Работа с платежными транзакциями")
public class TransactionController {

    private final TransactionService service;

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение транзакции по ее ID")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable @Parameter(description = "ID транзакции") String id) {
        return ResponseEntity.ok(service.getTransactionById(id));
    }

    @GetMapping("/user/{userId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение всех транзакций инвестора")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable @Parameter(description = "ID инвестора") String userId) {
        return ResponseEntity.ok(service.getTransactionsByUserId(userId));
    }

    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создание транзакции для инвестора")
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Parameter(description = "Объект для создания транзакции") CreateTransactionDTO createTransactionDTO) {
        return ResponseEntity.ok(service.createTransaction(createTransactionDTO));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновлеине транзакции инвестора")
    public ResponseEntity<Transaction> updateTransaction(
            @RequestBody @Parameter(description = "Объект для обновления транзакции") UpdateTransactionDTO updateTransactionDTO,
            @PathVariable @Parameter(description = "ID обновляемой транзакции") String id) {
        return ResponseEntity.ok(service.updateTransaction(updateTransactionDTO, id));
    }
}
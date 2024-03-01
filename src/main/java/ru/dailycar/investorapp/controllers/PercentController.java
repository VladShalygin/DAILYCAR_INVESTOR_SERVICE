package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.entities.Percent;
import ru.dailycar.investorapp.services.PercentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/investments/percent")
@Tag(name="Проценты", description = "Работа с процентами инвесторов")
public class PercentController {

    private final PercentService service;

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Список всех процентов")
    public ResponseEntity<List<Percent>> getPercents() {
        return ResponseEntity.ok(service.getAllPercents());
    }


    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Процент по его Id")
    public ResponseEntity<Percent> getPercents(@PathVariable @Valid @NotBlank String id) {
        return ResponseEntity.ok(service.getPercentById(id));
    }

    @GetMapping("/contract/{contractId}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Список всех процентов по договору")
    public ResponseEntity<List<Percent>> getPercentsByContractId(@PathVariable @Valid @NotBlank String contractId) {
        return ResponseEntity.ok(service.getPercentByContractId(contractId));
    }

}

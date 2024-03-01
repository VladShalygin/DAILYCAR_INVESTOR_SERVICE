package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.dto.CreateAgentPercentageDTO;
import ru.dailycar.investorapp.dto.UpdateAgentPercentageDTO;
import ru.dailycar.investorapp.entities.AgentPercentage;
import ru.dailycar.investorapp.entities.BankRequisite;
import ru.dailycar.investorapp.services.AgentPercentageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
@RequestMapping("/investments/agentPercentage")
@Tag(name = "Агентские проценты", description = "Проценты агентов, методы для их получения, создания и обновления")
public class AgentPercentageController {

    private final AgentPercentageService service;

    @GetMapping("/{id}")
    @CrossOrigin
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Агентские проценты по id")
    public ResponseEntity<AgentPercentage> getPercentageById(@PathVariable @Parameter(description = "Id процента") String id) {
        return ResponseEntity.ok(service.getPercentageById(id));
    };

    @GetMapping
    @CrossOrigin
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Все возможные агентские проценты")
    public ResponseEntity<List<AgentPercentage>> getPercentageById() {
        return ResponseEntity.ok(service.getPercentages());
    };

    @PutMapping
    @CrossOrigin
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление агентских процентов")
    public ResponseEntity<AgentPercentage> updatePercentage(
            @RequestBody @Parameter(description = "Сущность обновления" ) @Validated UpdateAgentPercentageDTO updateDto,
            @RequestBody @Parameter(description = "Id обновляемой сущности" ) @Validated @NotBlank String id) {
        return ResponseEntity.ok(service.updatePercentage(updateDto, id));
    };

    @PostMapping
    @CrossOrigin
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создание агентских процентов")
    public ResponseEntity<AgentPercentage> createPercentage(
            @RequestBody @Parameter(description = "Сущность создания" ) @Validated CreateAgentPercentageDTO createDto) {
        return ResponseEntity.ok(service.createPercentage(createDto));
    };


}

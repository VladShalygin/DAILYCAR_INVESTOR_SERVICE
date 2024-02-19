package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.dto.AgentInvitedUsers;
import ru.dailycar.investorapp.dto.UpdateUserDTO;
import ru.dailycar.investorapp.entities.User;
import ru.dailycar.investorapp.services.UserService;

@RestController
@RequestMapping("/investments/users")
@RequiredArgsConstructor
@Validated
@Tag(name="Пользователи", description = "Работа с пользователями")
public class UserController {

    private final UserService service;

    @CrossOrigin
    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение юзера по его ID")
    public ResponseEntity<User> getUserById(@PathVariable @Parameter(description = "ID пользователя") String id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление юзера")
    public ResponseEntity<User> updateUser(@RequestBody @Parameter(description = "Объект для обновления юзера") UpdateUserDTO updateUserDTO,
                                           @PathVariable @Parameter(description = "ID обновляемого пользователя") String id) {
        return ResponseEntity.ok(service.updateUser(updateUserDTO, id));
    }

    @CrossOrigin
    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение юзера по токену")
    public ResponseEntity<User> getUserByToken(@RequestParam @Parameter(description = "Токен юзера") String token) {
        return ResponseEntity.ok(service.getUserByToken(token));
    }

    @CrossOrigin
    @GetMapping("/referral")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение приглашенных пользователем(1 уровень)")
    public ResponseEntity<AgentInvitedUsers> getUserInvitedPeoples(@Valid @RequestParam @Parameter(description = "Реферальный код пользователя") @NotBlank String referralCode) {
        return ResponseEntity.ok(service.getUserInvitedPeoples(referralCode));
    }
}
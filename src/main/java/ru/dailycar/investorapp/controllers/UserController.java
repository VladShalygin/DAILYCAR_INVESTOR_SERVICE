package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.dto.UpdateUserDTO;
import ru.dailycar.investorapp.entities.User;
import ru.dailycar.investorapp.services.UserService;

@RestController
@RequestMapping("/investments/users")
@RequiredArgsConstructor
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

//    @PostMapping
//    @Operation(summary = "Создание пользователя")
//    public ResponseEntity<User> createUser(@RequestBody @Parameter(description = "Объект для создания пользователя") SignUpRequest createUserDTO) {
//        return ResponseEntity.ok(service.createUser(createUserDTO));
//    }

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
}
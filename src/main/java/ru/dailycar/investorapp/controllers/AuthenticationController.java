package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.dto.LoginRequest;
import ru.dailycar.investorapp.dto.LoginResponse;
import ru.dailycar.investorapp.dto.RefreshTokenRequest;
import ru.dailycar.investorapp.dto.SignUpRequest;
import ru.dailycar.investorapp.entities.CodeType;
import ru.dailycar.investorapp.entities.User;
import ru.dailycar.investorapp.services.AuthCodeService;
import ru.dailycar.investorapp.services.AuthenticationService;

@Controller
@RequiredArgsConstructor
@Validated
@RequestMapping("/investments/auth")
@Tag(name = "Авторизация", description = "Регистрация, авторизация пользователя в системе")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final AuthCodeService codeService;


    @CrossOrigin
    @PostMapping("/signup")
    @Operation(
            summary = "Регистрация пользователя"
    )
    public ResponseEntity<User> signup(@Valid @RequestBody SignUpRequest request) throws BadRequestException {
        if (!codeService.checkCode(request.getEmail(), request.getEmailCode(), CodeType.SIGNUP) && !codeService.checkCode(request.getPhoneNumber(), request.getPhoneCode(), CodeType.SIGNUP)) {
            throw new BadRequestException("Не верный код!");
        }
        return ResponseEntity.ok(authService.signUp(request));
    }

    @CrossOrigin
    @PostMapping("/login")
    @Operation(
            summary = "Авторизация пользователя в системе"
    )
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws BadRequestException {
        if (!codeService.checkCode(request.getUsername(), request.getCode(), CodeType.LOGIN)) {
            throw new BadRequestException("Не верный код!");
        }
        return ResponseEntity.ok(authService.login(request));

    }

    @CrossOrigin
    @PostMapping("/refresh")
    @Operation(
            summary = "Обновление токена авторизации"
    )
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @CrossOrigin
    @PostMapping("/createCode")
    @Operation(
            summary = "Создание кода для подтверждения пользователя!"
    )
    public ResponseEntity<String> createCode(@Valid @NotBlank @RequestParam String username,
                                             @NotBlank @RequestParam String type) {
        return ResponseEntity.ok(codeService.createCode(username, CodeType.valueOf(type)));
    }

    @CrossOrigin
    @PostMapping("/approveCode")
    @Operation(
            summary = "Проверка кода"
    )
    public ResponseEntity<Boolean> test(@Valid @NotBlank @RequestParam String code,
                                        @NotBlank @RequestParam String username,
                                        @NotBlank @RequestParam String type) throws BadRequestException {
        return ResponseEntity.ok(codeService.checkCode(username, code, CodeType.valueOf(type)));
    }

    @CrossOrigin
    @PostMapping("/changePassword")
    @Operation(
            summary = "Смена пароля"
    )
    public ResponseEntity<User> changePassword(@Valid @NotBlank @RequestParam String code,
                                               @NotBlank @RequestParam String username,
                                               @NotBlank @RequestParam String newPassword) throws BadRequestException {
        if (!codeService.checkCode(username, code, CodeType.CHANGE_PASSWORD)) {
            throw new BadRequestException("Не верный код!");
        }
        return ResponseEntity.ok(authService.changePassword(username, newPassword));
    }

    @CrossOrigin
    @PostMapping("/changeUsername")
    @Operation(

            summary = "Смена username"
    )
    public ResponseEntity<User> changePassword(@Valid @NotBlank @RequestParam String codeOldUsername,
                                               @NotBlank @RequestParam String codeNewUsername,
                                               @NotBlank @RequestParam String oldUsername,
                                               @NotBlank @RequestParam String newUsername) throws BadRequestException {
        if (!codeService.checkCode(oldUsername, codeOldUsername, CodeType.CHANGE_USERNAME) && !codeService.checkCode(newUsername, codeNewUsername, CodeType.CHANGE_USERNAME)) {
            throw new BadRequestException("Не верный код!");
        }
        return ResponseEntity.ok(authService.changePassword(oldUsername, newUsername));
    }

    @CrossOrigin
    @PostMapping("/clearCode")
    @Operation(
            summary = "Удаление кода"
    )
    public ResponseEntity<Boolean> deleteCode(@Valid @NotBlank @RequestParam String username,
                                              @NotBlank @RequestParam String type) throws BadRequestException {
        return ResponseEntity.ok(codeService.deleteCode(username, CodeType.valueOf(type)));
    }

}

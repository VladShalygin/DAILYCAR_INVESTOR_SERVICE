package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dailycar.investorapp.dto.LoginRequest;
import ru.dailycar.investorapp.dto.LoginResponse;
import ru.dailycar.investorapp.dto.RefreshTokenRequest;
import ru.dailycar.investorapp.dto.SignUpRequest;
import ru.dailycar.investorapp.entities.User;
import ru.dailycar.investorapp.services.AuthenticationService;

@Controller
@RequiredArgsConstructor
@Validated
@RequestMapping("/investments/auth")
@Tag(name = "Авторизация", description = "Регистрация, авторизация пользователя в системе")
public class AuthenticationController {

    private final AuthenticationService service;

    @CrossOrigin
    @PostMapping("/signup")
    @Operation(
            summary = "Регистрация пользователя"
    )
    public ResponseEntity<User> signup (@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(service.signUp(request));
    }

    @CrossOrigin
    @PostMapping("/login")
    @Operation(
            summary = "Авторизация пользователя в системе"
    )
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(service.login(request));
    }

    @CrossOrigin
    @PostMapping("/refresh")
    @Operation(
            summary = "Обновление токена авторизации"
    )
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

}

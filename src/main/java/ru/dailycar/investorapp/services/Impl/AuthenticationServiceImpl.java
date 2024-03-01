package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.LoginRequest;
import ru.dailycar.investorapp.dto.LoginResponse;
import ru.dailycar.investorapp.dto.RefreshTokenRequest;
import ru.dailycar.investorapp.dto.SignUpRequest;
import ru.dailycar.investorapp.entities.InvestorType;
import ru.dailycar.investorapp.entities.Role;
import ru.dailycar.investorapp.entities.User;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.exceptions.UserAlreadyExists;
import ru.dailycar.investorapp.repositories.UserRepository;
import ru.dailycar.investorapp.services.AuthenticationService;
import ru.dailycar.investorapp.services.JWTService;

import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    @Override
    public LoginResponse signUp(SignUpRequest request) {

        String normalizedPhoneNumber = normalizePhoneNumber(request.getPhoneNumber());

        boolean existedUserByPhone = repository.existsByPhoneNumber(normalizedPhoneNumber);
        boolean existedUserByEmail = repository.existsByEmail(request.getEmail().toLowerCase());

        if (existedUserByEmail || existedUserByPhone) throw new UserAlreadyExists();
        User user = repository.save(User.builder()
                .name(request.getName())
                .secondName(request.getSecondName())
                .surname(request.getSurname())
                .role(Role.INVESTOR)
                .type(InvestorType.valueOf(request.getType()))
                .phoneNumber(normalizedPhoneNumber)
                .email(request.getEmail().toLowerCase())
                .locked(false)
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
        return LoginResponse.builder()
                .token(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(new HashMap<>(), user))
                .id(user.getId())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername().toLowerCase(), request.getPassword()));
        User user = repository.findByEmailOrPhoneNumber(request.getUsername().toLowerCase()).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        return LoginResponse.builder()
                .token(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(new HashMap<>(), user))
                .id(user.getId())
                .build();
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        String username = jwtService.extractUserName(request.getToken());
        User user = repository.findByEmailOrPhoneNumber(username.toLowerCase()).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        if (jwtService.isTokenValid(request.getToken(), user)) {
            return LoginResponse.builder()
                    .token(jwtService.generateToken(user))
                    .refreshToken(request.getToken())
                    .build();
        }
        return null;
    }

    @Override
    public User changePassword(String username, String newPassword) {
        User user = repository.findByEmailOrPhoneNumber(username.toLowerCase()).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        user.setPassword(passwordEncoder.encode(newPassword));
        return repository.save(user);

    }

    @Override
    public User changeUsername(String oldUsername, String newUsername) {
        User user = repository.findByEmailOrPhoneNumber(oldUsername.toLowerCase()).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        if (oldUsername.contains("@") && newUsername.contains("@")) {
            user.setEmail(newUsername.toLowerCase());
        } else {
            user.setPhoneNumber(normalizePhoneNumber(newUsername.toLowerCase()));
        }
        return repository.save(user);
    }


    private String normalizePhoneNumber(String phoneNumber) {
        return phoneNumber
                .replaceAll("\\+7", "8")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll(" ", "")
                .replaceAll("-", "");
    }

    private String generateReferralCode() {
        String uuid = java.util.UUID.randomUUID().toString();
        String shortCode = RandomStringUtils.randomAlphanumeric(8);
        return uuid + shortCode;
    }


}

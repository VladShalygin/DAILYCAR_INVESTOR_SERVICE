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
    public User signUp(SignUpRequest request) {

        String normalizedPhoneNumber = normalizePhoneNumber(request.getPhoneNumber());

        boolean existedUserByPhone = repository.existsByPhoneNumber(normalizedPhoneNumber);
        boolean existedUserByEmail = repository.existsByEmail(request.getEmail());

        if (existedUserByEmail || existedUserByPhone) throw new UserAlreadyExists();
        System.out.println(request);
        return repository.save(User.builder()
                .name(request.getName())
                .secondName(request.getSecondName())
                .surname(request.getSurname())
                .role(Role.INVESTOR)
                .type(InvestorType.valueOf(request.getType()))
                .phoneNumber(normalizedPhoneNumber)
                .email(request.getEmail())
                .referralCode(generateReferralCode())
                .parentReferralCode(request.getParentReferralCode())
                .locked(false)
//                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());

    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = repository.findByEmailOrPhoneNumber(request.getUsername()).orElseThrow(IllegalArgumentException::new);
        return LoginResponse.builder()
                .token(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(new HashMap<>(), user))
                .id(user.getId())
                .build();
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        String username = jwtService.extractUserName(request.getToken());
        User user = repository.findByEmailOrPhoneNumber(username).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        System.out.println(user);
        if (jwtService.isTokenValid(request.getToken(), user)) {
            return LoginResponse.builder()
                    .token(jwtService.generateToken(user))
                    .refreshToken(request.getToken())
                    .build();
        }
        return null;
    }


    private String normalizePhoneNumber(String phoneNumber) {
        return phoneNumber
                .replaceAll("\\+7", "8")
//                .replaceAll("7", "8")
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

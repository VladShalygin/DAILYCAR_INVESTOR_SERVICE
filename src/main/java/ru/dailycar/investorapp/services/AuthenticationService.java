package ru.dailycar.investorapp.services;

import ru.dailycar.investorapp.dto.LoginResponse;
import ru.dailycar.investorapp.dto.RefreshTokenRequest;
import ru.dailycar.investorapp.dto.SignUpRequest;
import ru.dailycar.investorapp.dto.LoginRequest;
import ru.dailycar.investorapp.entities.User;

public interface AuthenticationService {

    User signUp(SignUpRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    User changePassword(String username, String newPassword);

    User changeUsername(String oldUsername, String newUsername);

}

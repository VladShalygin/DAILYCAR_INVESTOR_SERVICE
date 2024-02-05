package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Schema(description = "Роль пользователя")
@RequiredArgsConstructor
public enum Role {
    @Schema(description = "Инвестор")
    INVESTOR,

    @Schema(description = "Манагер")
    ACCOUNTANT

}

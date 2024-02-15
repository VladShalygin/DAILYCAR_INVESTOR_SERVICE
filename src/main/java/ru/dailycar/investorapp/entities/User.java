package ru.dailycar.investorapp.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document
@Data
@Builder
@Schema(description = "Сущность создаваемого пользователя")
public class User implements CustomUserDetails {

    @Id
    @Schema(description = "ID пользователя", example = "64804f7ab3afb023c6b9d397")
    private String id;

    @Schema(description = "Имя создаваемого пользователя", example = "Вася")
    private String name;

    @Schema(description = "Отчествого создаваемого пользователя", example = "Петрович")
    private String secondName;

    @Schema(description = "Фамилия создаваемого пользователя", example = "Иванов")
    private String surname;

    @Schema(description = "Роль пользователя", example = "INVESTOR")
    private Role role;

    @Schema(description = "Тип инвестор", example = "LEGAL")
    private InvestorType type;

    @Schema(description = "Номер телефона пользователя", example = "89994446677")
    private String phoneNumber;

    @Schema(description = "Электронная почта пользователя", example = "example@test.com")
    private String email;

    @Schema(description = "Реферальный код данного инвестора", example = "DSC2132FFa23")
    private String referralCode;

    @Schema(description = "Реферальный код инвестора, который пригласил данного пользователя", example = "DSC2132FFa23")
    private String parentReferralCode;

    @Schema(description = "Пароль пользователя")
    private String password;

    @Schema(description = "Активность аккаунта")
    private Boolean locked;

    @Schema(description = "Пол инвестора")
    private GenderType gender;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }


}

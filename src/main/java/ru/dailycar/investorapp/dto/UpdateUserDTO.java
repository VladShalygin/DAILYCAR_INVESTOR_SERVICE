package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

@Schema(description = "Сущность для обновления пользователя")
public class UpdateUserDTO {

    @Schema(description = "Имя создаваемого пользователя", example = "Вася")
    @Nullable
    private String name;

    @Schema(description = "Отчествого создаваемого пользователя", example = "Петрович")
    @Nullable
    private String secondName;

    @Schema(description = "Фамилия создаваемого пользователя", example = "Иванов")
    @Nullable
    private String surname;

    @Schema(description = "Номер телефона пользователя", example = "89994446677")
    @Nullable
    private String phoneNumber;

    @Schema(description = "Электронная почта пользователя", example = "example@test.com")
    @Nullable
    private String email;

}

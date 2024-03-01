package ru.dailycar.investorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

@Schema(description = "Сущность для обновления пользователя")
@Data
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

    @Schema(description = "Пол инвестора", example = "MALE")
    @Nullable
    private String gender;

    @Schema(description = "Дата рождения инвестора", example = "1708519434096")
    @Nullable
    private Long birthday;

    @Schema(description = "Тип лица", example = "LEGAL")
    @Nullable
    private String type;

}

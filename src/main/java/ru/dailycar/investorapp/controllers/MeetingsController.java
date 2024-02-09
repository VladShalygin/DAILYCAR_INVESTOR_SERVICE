package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.dto.CreateMeetingDto;
import ru.dailycar.investorapp.dto.UpdateMeetingDTO;
import ru.dailycar.investorapp.entities.Meeting;
import ru.dailycar.investorapp.services.MeetingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/investments/meetings")
@Tag(name = "Встречи", description = "получение, обновление и создание встреч")
public class MeetingsController {

    private final MeetingService service;

    @CrossOrigin
    @GetMapping("/{id}")
    @Operation(summary = "Получение встречи по ее Id")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Meeting> getMeetingById(@PathVariable @Validated @Parameter(description = "Id встречи") @NotBlank String id) {
        return ResponseEntity.ok(service.getMeetingById(id));
    }

    @CrossOrigin
    @GetMapping("/investor/{id}")
    @Operation(summary = "Получение встреч c пользователем по его Id")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<Meeting>> getMeetingByUserId(@PathVariable @Validated @Parameter(description = "Id встречи") @NotBlank String id) {
        return ResponseEntity.ok(service.getUserMeeting(id));
    }

    @CrossOrigin
    @GetMapping
    @Operation(summary = "Получение абсолютно всех встреч")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<Meeting>> getMeetings() {
        return ResponseEntity.ok(service.getMeetings());
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @Operation(summary = "Обновление встречи")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable @Parameter(description = "Id пользователя") String id,
                                                 @RequestBody @Parameter(description = "Сущность обновления") UpdateMeetingDTO updateDto) {
        return ResponseEntity.ok(service.updateMeeting(updateDto, id));
    }

    @CrossOrigin
    @PostMapping
    @Operation(summary = "Создание встречи")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Meeting> createMeeting(@RequestBody @Validated @Parameter(description = "Сущность создания") CreateMeetingDto createDto) throws BadRequestException {
        return ResponseEntity.ok(service.createMeeting(createDto));
    }

}

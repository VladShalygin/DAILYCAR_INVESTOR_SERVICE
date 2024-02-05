package ru.dailycar.investorapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.dto.CreateBidDTO;
import ru.dailycar.investorapp.dto.UpdateBidDTO;
import ru.dailycar.investorapp.entities.Bid;
import ru.dailycar.investorapp.services.BidService;

import java.util.List;

@RestController
@RequestMapping("/investments/bid")
@RequiredArgsConstructor
@Tag(name="Заявки на инвестиции", description="С помощью этих запросов можно создать заявки для инвесторов")
public class BidController {

    private final BidService service;

    @Operation(
            summary = "Получение всех заявок"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping()
    private ResponseEntity<List<Bid>> getBids() {
        var test = service.getBids();
        System.out.println(1);
        return ResponseEntity.ok(service.getBids());
    }

    @Operation(
            summary = "Получение заявки по ее id"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    private ResponseEntity<Bid> getBidById(@PathVariable @Parameter(description = "ID заявки") String id) {
        return ResponseEntity.ok(service.getBidById(id));
    }

    @Operation(
            summary = "Получение всех заявок инвестора по его айдишнику"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/investor/{userId}")
    private ResponseEntity<List<Bid>> getBidsByUserId(@PathVariable @Parameter(description = "ID пользователя") String userId) {
        return ResponseEntity.ok(service.getBidsByUserId(userId));
    }

    @Operation(
            summary = "Создание заявки на инвестицию"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping
    private ResponseEntity<Bid> createBid(@RequestBody @Parameter(description = "Сущность создаваемой заявки") CreateBidDTO createBidDTO) {
        return ResponseEntity.ok(service.createBid(createBidDTO));
    }

    @Operation(
            summary = "Обновление заявки на инвестицию",
            description = "В обновлении нужно передать только обновленные поля"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping(("/{id}"))
    private ResponseEntity<Bid> updateBid(@RequestBody @Parameter(description = "Сущность для обновления заявки, все поля не обязательны") UpdateBidDTO updateBidDTO,
                                          @PathVariable @Parameter(description = "ID cуществующей заявки ") String id) {
        return ResponseEntity.ok(service.updateBid(updateBidDTO, id));
    }

    @Operation(
            summary = "Удаление заявки по ее айдишнику"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    private ResponseEntity<Bid> deleteBid(@PathVariable @Parameter(description = "ID cуществующей заявки ") String id) {
        return ResponseEntity.ok(service.deleteBid(id));
    }

}

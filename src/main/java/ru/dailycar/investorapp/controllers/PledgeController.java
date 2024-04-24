package ru.dailycar.investorapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dailycar.investorapp.dto.CreatePledgeDto;
import ru.dailycar.investorapp.dto.UpdatePledgeDto;
import ru.dailycar.investorapp.entities.PledgeEntity;
import ru.dailycar.investorapp.services.PledgeService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/investments/pledge")
public class PledgeController {

    private final PledgeService pledgeService;

    @GetMapping("/{id}")
    public ResponseEntity<PledgeEntity> getPledgeById(@PathVariable String id){
        return ResponseEntity.ok(pledgeService.getPledgeById(id));
    }

    @GetMapping("/investor/{id}")
    public ResponseEntity<List<PledgeEntity>> getPledgeByUserId(@PathVariable String id){
        return ResponseEntity.ok(pledgeService.getPledgesByInvestorId(id));
    }

    @PostMapping()
    public ResponseEntity<PledgeEntity> createPledge(@RequestBody CreatePledgeDto createPledgeDto){
        return ResponseEntity.ok(pledgeService.createPledge(createPledgeDto));
    }

    @GetMapping("/photos/{sid}")
    public ResponseEntity<List<String>> getPledgePhotos(@PathVariable String sid){
        return ResponseEntity.ok(pledgeService.getPhotos(sid));
    }

    @PutMapping("/update/{sid}")
    public ResponseEntity<PledgeEntity> updatePledge(@RequestBody UpdatePledgeDto updatePledgeDto, @PathVariable String sid){
        return ResponseEntity.ok(pledgeService.updatePledge(updatePledgeDto, sid));
    }

}

package ru.dailycar.investorapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/investments-admin/")
public class TestController {

    @GetMapping()
    public String test() {
        return "ok";
    }

}

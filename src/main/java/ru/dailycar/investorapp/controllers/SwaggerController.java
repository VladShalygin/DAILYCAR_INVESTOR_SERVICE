package ru.dailycar.investorapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger-test")
public class SwaggerController {

    @GetMapping
    public String getSwagger() {

        return "test";
    }
}

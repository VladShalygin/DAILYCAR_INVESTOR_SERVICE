package ru.dailycar.investorapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class InvestorAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestorAppApplication.class, args);
    }

}

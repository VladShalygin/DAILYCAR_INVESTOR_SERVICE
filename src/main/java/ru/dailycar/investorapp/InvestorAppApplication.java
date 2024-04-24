package ru.dailycar.investorapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication

@EnableScheduling
@EnableDiscoveryClient
public class InvestorAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestorAppApplication.class, args);
    }

}

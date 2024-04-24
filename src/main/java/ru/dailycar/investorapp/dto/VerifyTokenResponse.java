package ru.dailycar.investorapp.dto;

import lombok.Data;

@Data
public class VerifyTokenResponse {
    private boolean ok;
    private String id;
    private String role;
}

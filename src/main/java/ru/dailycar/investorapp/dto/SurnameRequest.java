package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SurnameRequest {
    private List<String> userIds;
    private boolean needPhone;
}

package ru.dailycar.investorapp.entities;

import lombok.Data;

import java.util.List;

@Data
public class CarPhotoResponse {
    private final List<String> photos;
}

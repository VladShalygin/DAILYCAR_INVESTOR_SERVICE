package ru.dailycar.investorapp.sources.Impl;

import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.dailycar.investorapp.entities.CarPhotoResponse;
import ru.dailycar.investorapp.entities.PledgeType;
import ru.dailycar.investorapp.sources.PledgeSource;

import java.util.ArrayList;
import java.util.List;

@Component
public class PledgeSourceImpl implements PledgeSource {

    @Value("${link.car}")
    String urlCarPhotos;

    @Override
    public List<String> getPhotos(String sid, PledgeType type) {

        switch (type){
            case CAR -> {
                    try {
                        ResponseEntity<CarPhotoResponse> result =  RestClient.create().get()
                                .uri(urlCarPhotos + sid)
                                .retrieve()
                                .toEntity(CarPhotoResponse.class);

                        return result.getBody().getPhotos();
                    } catch (NullPointerException e) {
                        throw new BadRequestException();
                    }

            }
            case LAND, HOUSE -> {
                return new ArrayList<>();
            }
            default -> throw new IllegalArgumentException();
        }

    }
}

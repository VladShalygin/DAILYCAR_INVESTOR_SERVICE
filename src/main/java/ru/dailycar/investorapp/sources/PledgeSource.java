package ru.dailycar.investorapp.sources;

import ru.dailycar.investorapp.entities.PledgeType;

import java.util.List;

public interface PledgeSource {

    public List<String> getPhotos(String sid, PledgeType type);
}

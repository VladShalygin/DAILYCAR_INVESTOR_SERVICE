package ru.dailycar.investorapp.sources;

import ru.dailycar.investorapp.dto.InvestorsNames;
import ru.dailycar.investorapp.dto.UserIdsProjection;

import java.util.List;
import java.util.Map;

public interface UserSource {

    public List<InvestorsNames> getUsersNamesByIds(List<UserIdsProjection> userIds, String token);

}

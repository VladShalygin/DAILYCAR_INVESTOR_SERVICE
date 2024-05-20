package ru.dailycar.investorapp.sources;

import ru.dailycar.investorapp.dto.*;

import java.util.List;
import java.util.Map;

public interface UserSource {

    public List<InvestorsNames> getUsersNamesByIds(List<UserIdsProjection> userIds, String token);

    InvestorsCount getInvestorCount(String token);

    List<SurnameWithInitials> getSurnameWithInitials(String token, SurnameRequest surnameRequest);

    List<ActiveInvestorInfo> getActiveInvestorInfo(String token);
}

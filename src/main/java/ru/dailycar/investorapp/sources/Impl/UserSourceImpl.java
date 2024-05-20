package ru.dailycar.investorapp.sources.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.dailycar.investorapp.dto.*;
import ru.dailycar.investorapp.sources.UserSource;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserSourceImpl implements UserSource {

    @Value("${link.user}")
    private String urlUsers;

    @Value("${link.investorsAdminUser}")
    private String urlInvestorsAdminUsers;

    @Override
    public List<InvestorsNames> getUsersNamesByIds(List<UserIdsProjection> usersIds, String token) {
        return RestClient.create()
                .post()
                .uri(urlUsers + "investorsIds")
                .header("Authorization", token)
                .body(usersIds)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<InvestorsNames>>() {
                })
                .getBody();
    }

    @Override
    public InvestorsCount getInvestorCount(String token) {
        return RestClient.create()
                .get()
                .uri(urlInvestorsAdminUsers + "investors/investorsCount")
                .header("Authorization", token)
                .retrieve()
                .toEntity(InvestorsCount.class)
                .getBody();
    }

    @Override
    public List<SurnameWithInitials> getSurnameWithInitials(String token, SurnameRequest surnameRequest) {
        return RestClient.create()
                .post()
                .uri(urlInvestorsAdminUsers + "investors/getSurnameWithInitialsAndPhone")
                .body(surnameRequest)
                .header("Authorization", token)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<SurnameWithInitials>>() {})
                .getBody();
    }

    @Override
    public List<ActiveInvestorInfo> getActiveInvestorInfo(String token) {
        return RestClient.create()
                .get()
                .uri(urlInvestorsAdminUsers + "investors/getActiveInvestorInfo")
                .header("Authorization", token)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<ActiveInvestorInfo>>() {})
                .getBody();
    }


}

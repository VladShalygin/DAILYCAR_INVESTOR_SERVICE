package ru.dailycar.investorapp.sources.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.dailycar.investorapp.dto.InvestorsNames;
import ru.dailycar.investorapp.dto.UserIdsProjection;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.sources.UserSource;
import ru.dailycar.investorapp.dto.VerifyTokenResponse;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class UserSourceImpl implements UserSource {

    @Value("${link.user}")
    private String urlUsers;

    @Value("${link.auth}")
    private String urlAuth;

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

}

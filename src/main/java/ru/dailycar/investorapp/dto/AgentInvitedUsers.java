package ru.dailycar.investorapp.dto;

import lombok.Builder;
import lombok.Data;
import ru.dailycar.investorapp.entities.User;

import java.util.List;

@Builder
@Data
public class AgentInvitedUsers {
    private List<User> usersFirstLevel;
//    private List<User> usersSecondLevel;

}

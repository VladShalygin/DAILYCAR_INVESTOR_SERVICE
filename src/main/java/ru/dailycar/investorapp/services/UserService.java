package ru.dailycar.investorapp.services;

import ru.dailycar.investorapp.dto.AgentInvitedUsers;
import ru.dailycar.investorapp.dto.UpdateUserDTO;
import ru.dailycar.investorapp.entities.User;


public interface UserService {

    User getUserById(String id);

//    public User createUser(CreateUserDTO createUserDTO);

    User updateUser(UpdateUserDTO updateUserDTO, String id);

    CustomUserDetailsService userDetailsService();

    User getUserByToken(String token);

}

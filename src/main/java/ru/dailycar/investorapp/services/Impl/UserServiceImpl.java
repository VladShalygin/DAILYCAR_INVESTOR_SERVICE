package ru.dailycar.investorapp.services.Impl;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.UpdateUserDTO;
import ru.dailycar.investorapp.entities.CustomUserDetails;
import ru.dailycar.investorapp.entities.User;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.UserRepository;
import ru.dailycar.investorapp.services.CustomUserDetailsService;
import ru.dailycar.investorapp.services.JWTService;
import ru.dailycar.investorapp.services.UserService;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper = new ModelMapper();;
    private final UserRepository repository;
    private final JWTService jwtService;

    @Override
    public User getUserById( @NotBlank String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует!"));
    }

    @Override
    public User updateUser(UpdateUserDTO updateUserDTO, @NotBlank String id) {
        User existedUser = repository.findById(id).orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует!"));
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(updateUserDTO, existedUser);
        return repository.save(existedUser);
    }

    @Override
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService() {
            @Override
            public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return repository.findByEmailOrPhoneNumber(username).orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует!"));
            }
        };
    }

    @Override
    public User getUserByToken(String token) {
        String username = jwtService.extractUserName(token);
        return repository.findByEmailOrPhoneNumber(username).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
    }
}

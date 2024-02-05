package ru.dailycar.investorapp.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.dailycar.investorapp.entities.CustomUserDetails;

public interface CustomUserDetailsService extends UserDetailsService {

    @Override
    CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}

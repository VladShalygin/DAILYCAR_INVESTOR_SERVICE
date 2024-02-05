package ru.dailycar.investorapp.entities;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {

    String getPhoneNumber();
}

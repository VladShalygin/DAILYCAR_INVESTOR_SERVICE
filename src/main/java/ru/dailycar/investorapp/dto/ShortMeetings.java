package ru.dailycar.investorapp.dto;

import lombok.Builder;
import ru.dailycar.investorapp.entities.MeetingType;

@Builder
public class ShortMeetings {
    private String id;
    private MeetingType type;
    private long date;
}

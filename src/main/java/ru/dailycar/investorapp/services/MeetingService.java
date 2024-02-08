package ru.dailycar.investorapp.services;

import org.apache.coyote.BadRequestException;
import ru.dailycar.investorapp.dto.CreateMeetingDto;
import ru.dailycar.investorapp.dto.UpdateMeetingDTO;
import ru.dailycar.investorapp.entities.Meeting;

import java.util.List;

public interface MeetingService {

    public Meeting getMeetingById(String id);

    public List<Meeting> getMeetings();

    public List<Meeting> getUserMeeting(String userId);

    public Meeting updateMeeting(UpdateMeetingDTO meetingDto, String id);

    public Meeting createMeeting(CreateMeetingDto meetingDto) throws BadRequestException;

}

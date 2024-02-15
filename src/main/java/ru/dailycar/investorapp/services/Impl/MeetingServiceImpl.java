package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreateMeetingDto;
import ru.dailycar.investorapp.dto.UpdateMeetingDTO;
import ru.dailycar.investorapp.entities.Meeting;
import ru.dailycar.investorapp.entities.MeetingStatus;
import ru.dailycar.investorapp.entities.MeetingType;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.MeetingRepository;
import ru.dailycar.investorapp.services.MeetingService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository repository;

    private final ModelMapper mapper;

    @Override
    public Meeting getMeetingById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Встречи с данным Id не существует!"));
    }

    @Override
    public List<Meeting> getMeetings() {
        return repository.findAll();
    }

    @Override
    public List<Meeting> getUserMeeting(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Meeting updateMeeting(UpdateMeetingDTO meetingDto, String id) {
        Meeting existedMeeting = repository.findById(id).orElseThrow(() -> new NotFoundException("Встречи с данным Id не существует!"));
        mapper.map(meetingDto, existedMeeting);
        System.out.println(meetingDto.toString());
        System.out.println(existedMeeting.toString());
        return repository.save(existedMeeting);
    }

    @Override
    public Meeting createMeeting(CreateMeetingDto meetingDto) throws BadRequestException {
        Meeting newMeeting = Meeting
                .builder()
                .userId(meetingDto.getUserId())
                .date(meetingDto.getDate())
                .type(MeetingType.valueOf(meetingDto.getType()))
                .status(MeetingStatus.PLANNED)
                .build();

        if (StringUtils.isNotBlank(meetingDto.getBidId())) {
            newMeeting.setBidId(meetingDto.getBidId());
        }else if (StringUtils.isNotBlank(meetingDto.getContractId())) {
            newMeeting.setContractId(meetingDto.getContractId());
        } else {
            throw new BadRequestException("ContractId и bidId не могут быть null");
        }

        if (StringUtils.isNotBlank(meetingDto.getDescription())) {
            newMeeting.setDescription(meetingDto.getDescription());
        }

        return repository.save(newMeeting);

    }
}

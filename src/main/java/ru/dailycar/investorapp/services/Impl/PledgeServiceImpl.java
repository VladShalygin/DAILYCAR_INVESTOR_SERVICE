package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreatePledgeDto;
import ru.dailycar.investorapp.dto.UpdatePledgeDto;
import ru.dailycar.investorapp.entities.Contract;
import ru.dailycar.investorapp.entities.PledgeEntity;
import ru.dailycar.investorapp.entities.PledgeStatus;
import ru.dailycar.investorapp.entities.PledgeType;
import ru.dailycar.investorapp.exceptions.AlreadyExistsException;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.ContractRepository;
import ru.dailycar.investorapp.repositories.PledgeRepository;
import ru.dailycar.investorapp.services.PledgeService;
import ru.dailycar.investorapp.sources.PledgeSource;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PledgeServiceImpl implements PledgeService {

    private final PledgeRepository pledgeRepository;
    private final ContractRepository contractRepository;
    private final ModelMapper mapper;
    private final PledgeSource pledgeSource;

    @Override
    public PledgeEntity getPledgeById(String id) {
        return pledgeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<PledgeEntity> getPledgesByInvestorId(String userId) {
        List<Contract> userContracts = contractRepository.findByUserId(userId);

        if (userContracts.isEmpty()) throw new NotFoundException();

        List<PledgeEntity> pledges = new ArrayList<>();
        for (Contract contract : userContracts) {
            if (contract.getPledgeId() != null) {
                PledgeEntity pledge = pledgeRepository.findById(contract.getPledgeId()).orElse(null);
                if (pledge == null) continue;
                pledges.add(pledge);
            }
        }
        ;
        return pledges;
    }

    @Override
    public PledgeEntity createPledge(CreatePledgeDto pledgeDto) {
        PledgeEntity existedPledge = pledgeRepository.findBySid(pledgeDto.getSid());

        if(existedPledge != null) {
            throw new AlreadyExistsException();
        }

        PledgeEntity pledge = PledgeEntity.builder()
                .type(PledgeType.valueOf(pledgeDto.getType()))
                .sid(pledgeDto.getSid())
                .status(PledgeStatus.AVAILABLE)
                .build();

        if (pledge.getType().equals(PledgeType.CAR) && pledgeDto.getNumber() != null) {
            pledge.setNumber(pledgeDto.getNumber());
        }
        return pledgeRepository.save(pledge);
    }

    @Override
    public PledgeEntity updatePledge(UpdatePledgeDto pledgeDto, String sid) {
        PledgeEntity existingPledge = pledgeRepository.findBySid(sid);
        if (existingPledge == null) {
            throw new NotFoundException();
        }
        mapper.map(pledgeDto, existingPledge);
        return pledgeRepository.save(existingPledge);
    }

    @Override
    public List<String> getPhotos(String sid) {
        PledgeEntity existingPledge = pledgeRepository.findBySid(sid);
        if (existingPledge == null) throw new NotFoundException();
        return pledgeSource.getPhotos(sid, existingPledge.getType());
    }
}

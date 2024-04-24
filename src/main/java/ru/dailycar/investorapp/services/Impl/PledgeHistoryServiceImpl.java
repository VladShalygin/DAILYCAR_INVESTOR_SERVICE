package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.entities.PledgeHistory;
import ru.dailycar.investorapp.repositories.PledgeHistoryRepository;
import ru.dailycar.investorapp.services.PledgeHistoryService;

@Service
@RequiredArgsConstructor
public class PledgeHistoryServiceImpl implements PledgeHistoryService {

    private final PledgeHistoryRepository pledgeHistoryRepository;

    @Override
    public PledgeHistory savePledgeHistory(PledgeHistory history) {
        return pledgeHistoryRepository.save(history);
    }
}

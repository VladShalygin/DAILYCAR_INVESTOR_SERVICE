package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreateBankRequisitesDTO;
import ru.dailycar.investorapp.dto.UpdateBankRequisiteDTO;
import ru.dailycar.investorapp.entities.BankRequisite;
import ru.dailycar.investorapp.services.BankRequisiteService;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.BankRequisiteRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BankRequisiteServiceImpl implements BankRequisiteService {

    private final BankRequisiteRepository repository;

    private final ModelMapper mapper;

    @Override
    public BankRequisite getBankRequisiteById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Реквизиты не найдены!"));
    }

    @Override
    public List<BankRequisite> getBankRequisiteByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public BankRequisite createBankRequisite(CreateBankRequisitesDTO requisite) {
        return repository.save(
                BankRequisite
                        .builder()
                        .userId(requisite.userId)
                        .name(requisite.name)
                        .kpp(!StringUtils.isBlank(requisite.kpp) ? requisite.kpp : "empty")
                        .inn(requisite.inn)
                        .bankName(requisite.bankName)
                        .bankKpp(requisite.bankKpp)
                        .bankBik(requisite.bankBik)
                        .bankInn(requisite.bankInn)
                        .bankCorrespondentAccount(requisite.bankCorrespondentAccount)
                        .account(requisite.account)
                        .build());
    }

    @Override
    public BankRequisite updateBankRequisite(UpdateBankRequisiteDTO requisite, String id) {
        BankRequisite existingBankRequisite = repository.findById(id).orElseThrow(() -> new NotFoundException("Реквизиты не найдены"));
        mapper.map(requisite, existingBankRequisite);
        return repository.save(existingBankRequisite);
    }
}

package ru.dailycar.investorapp.services;

import ru.dailycar.investorapp.dto.CreateBankRequisitesDTO;
import ru.dailycar.investorapp.dto.UpdateBankRequisiteDTO;
import ru.dailycar.investorapp.entities.BankRequisite;

import java.util.List;

public interface BankRequisiteService {

    BankRequisite getBankRequisiteById(String id);

    List<BankRequisite> getBankRequisiteByUserId(String userId);

    BankRequisite createBankRequisite(CreateBankRequisitesDTO requisite);

    BankRequisite updateBankRequisite(UpdateBankRequisiteDTO requisite, String id);
}

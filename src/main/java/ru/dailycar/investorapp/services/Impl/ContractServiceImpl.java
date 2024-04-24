package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dailycar.investorapp.dto.CreateContractDTO;
import ru.dailycar.investorapp.dto.InvestorsNames;
import ru.dailycar.investorapp.dto.UpdateContractDTO;
import ru.dailycar.investorapp.dto.UserIdsProjection;
import ru.dailycar.investorapp.entities.Contract;
import ru.dailycar.investorapp.entities.ContractStatus;
import ru.dailycar.investorapp.entities.PledgeHistory;
import ru.dailycar.investorapp.exceptions.BadRequestException;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.ContractRepository;
import ru.dailycar.investorapp.services.ContractService;
import ru.dailycar.investorapp.services.PledgeHistoryService;
import ru.dailycar.investorapp.services.PledgeService;
import ru.dailycar.investorapp.sources.UserSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ContractServiceImpl implements ContractService {

    @Value("${CONTRACTS_UPLOAD_DIR}")
    private String UPLOAD_DIR;

    private final ContractRepository repository;
    private final UserSource userSource;
    private final PledgeHistoryService pledgeHistoryService;
    private final PledgeService pledgeService;

    private final ModelMapper mapper;

    @Override
    public List<Contract> getContracts() {
        return repository.findAll();
    }

    @Override
    public Contract getContractById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Договор не найден!"));
    }

    @Override
    public List<Contract> getContractsByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Contract createContract(CreateContractDTO createContractDTO) {
        long oneYearInMillis = 31557600000L;
        Contract parentContract = null;
        if (createContractDTO.getAgentContractId() != null) {
            parentContract = getContractById(createContractDTO.getAgentContractId());
            if (parentContract == null || !parentContract.getStatus().equals(ContractStatus.ACTIVE))
                throw new RuntimeException("Реферальный контракт не активен!");
        }
        return repository.save(Contract
                .builder()
                .userId(createContractDTO.getUserId())
                .pledgeId(createContractDTO.getPledgeId())
                .dateCreate(System.currentTimeMillis())
                .dateExpiration(System.currentTimeMillis() + oneYearInMillis)
                .status(ContractStatus.ACTIVE)
                .type(createContractDTO.getType())
                .amount(createContractDTO.getAmount())
                .percent(createContractDTO.getPercent())
                .agentPercentageId(parentContract != null ? createContractDTO.getAgentPercentageId() : "empty")
                .parentContractId(parentContract != null ? parentContract.getId() : "empty")
                .number(getContractNumber())
                .build());
    }

    @Override
    public Contract updateContract(UpdateContractDTO updateContractDTO, String id) {
        Contract existingContract = repository.findById(id).orElseThrow(() -> new NotFoundException("Договора с таким айди не существует!"));
        mapper.map(updateContractDTO, existingContract);
        return repository.save(existingContract);
    }

    @Override
    public Contract uploadContract(MultipartFile file, String id) throws IOException {
        Contract existedContract = getContractById(id);

        String filename = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path filepath = Paths.get(UPLOAD_DIR, filename);
        Files.createDirectories(filepath.getParent());
        Files.write(filepath, file.getBytes());

        existedContract.setPath("/contracts/" + filename);
        return repository.save(existedContract);

    }

    @Override
    public List<Contract> getActiveNonAgentContracts() {
        return repository.findActiveNonAgentContracts();
    }

    @Override
    public List<Contract> getContractsByAgentContractId(String contractId) {
        return repository.findByParentContractId(contractId);
    }

    @Override
    public List<InvestorsNames> getInvestorsNameByParentReferralCode(String parentReferralCode, String authorizationHeader) {
        getContractById(parentReferralCode);
        List<UserIdsProjection> usersIds = repository.findUserIdByParentReferralCode(parentReferralCode);
        if (usersIds.isEmpty()) {
            return new ArrayList<>();
        }
        return userSource.getUsersNamesByIds(usersIds, authorizationHeader);
    }

    @Override
    public Contract changePledge(String contractId, String newPledgeId, String userId) {
        Contract contract = getContractById(contractId);
        pledgeService.getPledgeById(newPledgeId);
        if (repository.getContractByPledgeId(newPledgeId) != null) throw new BadRequestException("Данное залоговое имущество занято!");

        pledgeHistoryService.savePledgeHistory(
                PledgeHistory.builder()
                        .executor(userId)
                        .newPledge(newPledgeId)
                        .oldPledge(contract.getPledgeId())
                        .date(System.currentTimeMillis())
                        .build());
        contract.setPledgeId(newPledgeId);
        return repository.save(contract);
    }

    private String getContractNumber() {
        String part1 = "D";
        String part2 = String.valueOf(repository.count() + 1);
        String part3 = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return part1 + part2 + part3;
    }

}

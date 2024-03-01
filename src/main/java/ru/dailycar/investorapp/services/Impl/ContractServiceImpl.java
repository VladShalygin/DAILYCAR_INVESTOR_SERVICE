package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dailycar.investorapp.dto.CreateContractDTO;
import ru.dailycar.investorapp.dto.UpdateContractDTO;
import ru.dailycar.investorapp.entities.Contract;
import ru.dailycar.investorapp.entities.ContractStatus;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.ContractRepository;
import ru.dailycar.investorapp.services.ContractService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ContractServiceImpl implements ContractService {

    @Value("${CONTRACTS_UPLOAD_DIR}")
    private String UPLOAD_DIR;
    private final ContractRepository repository;

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
                .agentPercentageId(createContractDTO.getAgentPercentageId())
                .parentContractId(createContractDTO.getAgentContractId())
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
    public List<Contract> getActiveContracts() {
        return repository.findActiveContracts();
    }

}

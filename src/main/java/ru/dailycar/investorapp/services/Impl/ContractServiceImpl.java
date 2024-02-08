package ru.dailycar.investorapp.services.Impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreateContractDTO;
import ru.dailycar.investorapp.dto.UpdateContractDTO;
import ru.dailycar.investorapp.entities.Contract;
import ru.dailycar.investorapp.entities.ContractStatus;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.ContractRepository;
import ru.dailycar.investorapp.services.ContractService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository repository;

    private final ModelMapper mapper;

    @Override
    public List<Contract> getContracts() {
        return repository.findAll();
    }

    @Override
    public Contract getContractById(@Valid @NotBlank String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Договор не найден!"));
    }

    @Override
    public List<Contract> getContractsByUserId(@Valid @NotBlank String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Contract createContract(@Valid CreateContractDTO createContractDTO) {
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
                .build());
    }

    @Override
    public Contract updateContract(UpdateContractDTO updateContractDTO, @Valid @NotBlank String id) {
        Contract existingContract = repository.findById(id).orElseThrow(() -> new  NotFoundException("Договора с таким айди не существует!"));
        mapper.map(updateContractDTO, existingContract);
        return repository.save(existingContract);
    }
}

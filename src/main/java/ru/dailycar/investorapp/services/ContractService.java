package ru.dailycar.investorapp.services;


import org.springframework.web.multipart.MultipartFile;
import ru.dailycar.investorapp.dto.CreateContractDTO;
import ru.dailycar.investorapp.dto.UpdateContractDTO;
import ru.dailycar.investorapp.entities.Contract;

import java.io.IOException;
import java.util.List;

public interface ContractService {
    public List<Contract> getContracts();

    public Contract getContractById(String id);

    public List<Contract> getContractsByUserId(String userId);

    public Contract createContract(CreateContractDTO createContractDTO);

    public Contract updateContract(UpdateContractDTO updateContractDTO, String id);

    public Contract uploadContract(MultipartFile file, String id) throws IOException;

    public List<Contract> getActiveContracts();

}

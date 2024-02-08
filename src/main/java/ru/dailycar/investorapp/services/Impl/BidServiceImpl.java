package ru.dailycar.investorapp.services.Impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreateBidDTO;
import ru.dailycar.investorapp.entities.Bid;
import ru.dailycar.investorapp.entities.BidStatus;
import ru.dailycar.investorapp.dto.UpdateBidDTO;
import ru.dailycar.investorapp.entities.ContractType;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.BidRepository;
import ru.dailycar.investorapp.services.BidService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<Bid> getBids() {
        return repository.findAll();
    }

    @Override
    public Bid getBidById(@Valid @NotBlank String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Заявка не найдена!"));
    }

    @Override
    public List<Bid> getBidsByUserId(@Valid @NotBlank String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Bid createBid(@Valid CreateBidDTO bidDTO) {
        Bid bid = Bid.builder()
                    .amount(bidDTO.getAmount())
                    .status(BidStatus.CREATED)
                    .type(ContractType.valueOf(bidDTO.getType()))
                    .userId(bidDTO.getUserId())
                    .description("")
                    .build();
        return repository.save(bid);
    }

    @Override
    public Bid updateBid(UpdateBidDTO updateBidDTO, @Valid @NotBlank String id) {
        Bid existingBid = repository.findById(id).orElseThrow(() -> new NotFoundException("Заявка не найдена"));
        mapper.map(updateBidDTO, existingBid);
        return repository.save(existingBid);
    }

    @Override
    public Bid deleteBid(@Valid @NotBlank String id) {
        Bid existingBid = repository.findById(id).orElseThrow(() -> new NotFoundException("Заявка не найдена"));
        existingBid.setStatus(BidStatus.DELETED);
        return repository.save(existingBid);
    }
}

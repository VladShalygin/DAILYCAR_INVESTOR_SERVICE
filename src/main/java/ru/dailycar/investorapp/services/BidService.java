package ru.dailycar.investorapp.services;

import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.CreateBidDTO;
import ru.dailycar.investorapp.entities.Bid;
import ru.dailycar.investorapp.dto.UpdateBidDTO;

import java.util.List;

@Service
public interface BidService {

    public List<Bid> getBids();

    public Bid getBidById(String id);

    public List<Bid> getBidsByUserId(String userId);

    public Bid createBid(CreateBidDTO bidDTO);

    public Bid updateBid(UpdateBidDTO updateBidDTO, String id);

    public Bid deleteBid(String id);

}

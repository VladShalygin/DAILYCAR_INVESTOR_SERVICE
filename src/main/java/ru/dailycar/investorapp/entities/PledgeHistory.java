package ru.dailycar.investorapp.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "pledgeHistories")
public class PledgeHistory {

    @Id
    private String id;
    private String executor;
    private String oldPledge;
    private String newPledge;
    private Long date;

}

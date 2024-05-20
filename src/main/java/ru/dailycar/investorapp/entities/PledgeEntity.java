package ru.dailycar.investorapp.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pledges")
@Data
@Builder
@Setter
public class PledgeEntity {

    @Id
    private final String id;

    private  PledgeType type;

    private  String sid;

    private  String number;

    private PledgeStatus status;

}

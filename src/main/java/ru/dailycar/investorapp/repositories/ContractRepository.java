package ru.dailycar.investorapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dailycar.investorapp.dto.UserIdsProjection;
import ru.dailycar.investorapp.entities.Contract;

import java.util.List;

@Repository
public interface ContractRepository extends MongoRepository<Contract, String> {
    List<Contract> findByUserId(String userId);

    @Query(value = "{'status':  'ACTIVE', 'type':  {$ne:  'AGENT'}}")
    List<Contract> findActiveNonAgentContracts();

    @Query(value = "{'parentContractId':  ?0}")
    List<Contract> findByParentContractId(String contractId);

    @Query(value = "{parentContractId: ?0}", fields = "{userId: 1, _id: 0}")
    List<UserIdsProjection> findUserIdByParentReferralCode(String parentReferralCode);

    @Query(value = "{'pledgeId': ?0}")
    Contract getContractByPledgeId(String pledgeId);

    @Query(value = "{'parentContractId': ?0, 'status': 'ACTIVE'}", count = true)
    Integer countActiveParentContract(String parentContractId, long currentTimeMillis);

    @Query(value = "{'dateCreate':  {$gte: ?0 }, 'status': 'ACTIVE', 'type':  ?1}")
    int countActiveByTime(long time, String type);

    @Query(value = "{'dateCreate':  {$gte: ?0 }, 'status': 'EXPIRATION', 'type':  ?1}")
    int countTerminatedByTime(long time, String type);

    @Query(value = "{'userId':  ?0, 'status': ?1}", count = true)
    int findContractsByUserId(String userId, String status);
}


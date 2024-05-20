package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.*;
import ru.dailycar.investorapp.entities.*;
import ru.dailycar.investorapp.repositories.*;
import ru.dailycar.investorapp.sources.UserSource;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.dailycar.investorapp.entities.TransactionType.INVESTMENT;


@Service
@RequiredArgsConstructor
public class DtoBuilderServiceImpl {

    private final TransactionRepository transactionRepository;
    private final ContractRepository contractRepository;
    private final BidRepository bidRepository;
    private final PledgeRepository pledgeRepository;
    private final MeetingRepository meetingRepository;
    private final DocumentPhotoRepository documentPhotoRepository;

    private final UserSource userSource;

    ZoneId zoneId = ZoneId.of("Europe/Moscow");
    ZoneOffset zoneOffset = ZoneOffset.of("+03:00");


    public InvestmentsAmount getInvestmentsAmount() {
        long dayStartTime = LocalDate.now().atStartOfDay().toEpochSecond(zoneOffset) * 1000;

        List<Transaction> allInvestments = contractRepository.findActiveNonAgentContracts().stream()
                .flatMap(contract -> transactionRepository
                        .findTransactionsByContractId(contract.getId())
                        .stream()
                        .filter(transaction -> transaction.getType().equals(INVESTMENT))
                )
                .toList();
        return InvestmentsAmount
                .builder()
                .allInvest(
                        allInvestments
                                .stream()
                                .mapToInt(Transaction::getAmount)
                                .sum()
                )
                .dayInvestments(
                        allInvestments
                                .stream()
                                .filter(transaction -> transaction.getDate() > dayStartTime)
                                .mapToInt(Transaction::getAmount)
                                .sum()
                )
                .monthInvestments(
                        allInvestments
                                .stream()
                                .filter(transaction -> transaction.getDate() > dayStartTime - 24L * 60 * 60 * 1000 * (LocalDate.now().lengthOfMonth() - 1))
                                .mapToInt(Transaction::getAmount)
                                .sum()
                )
                .yearInvestments(
                        allInvestments
                                .stream()
                                .filter(transaction -> transaction.getDate() > dayStartTime - 24L * 60 * 60 * 1000 * (LocalDate.now().lengthOfYear() - 1))
                                .mapToInt(Transaction::getAmount)
                                .sum()
                )
                .build();
    }

    public BidsCount getBidCount() {
        return BidsCount
                .builder()
                .newBids(bidRepository.countByStatus(BidStatus.CREATED.toString()))
                .workBids(bidRepository.countByStatus(BidStatus.PROCESSED.toString()))
                .completedBids(bidRepository.countByStatus(BidStatus.COMPLETED.toString()))
                .build();
    }

    public InvestorsCount getInvestorsCount(String token) {
        return userSource.getInvestorCount(token);
    }

    public PledgeCount getPledgeCount() {
        return PledgeCount
                .builder()
                .availablePledge(pledgeRepository.countByStatus(PledgeStatus.AVAILABLE.toString()))
                .bookedPledge(pledgeRepository.countByStatus(PledgeStatus.BOOKED.toString()))
                .unavailablePledge(pledgeRepository.countByStatus(PledgeStatus.UNAVAILABLE.toString()))
                .inPledge(pledgeRepository.countByStatus(PledgeStatus.IN_PLEDGE.toString()))
                .build();
    }

    public ContractsCounts getContractsCounts(ContractType type) {
        long dayAgo = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long weekAgo = LocalDate.now().minusWeeks(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long monthAgo = LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long yearAgo = LocalDate.now().minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return ContractsCounts
                .builder()
                .dayCreatedContracts(contractRepository.countActiveByTime(dayAgo, type.toString()))
                .dayTerminatedContracts(contractRepository.countTerminatedByTime(dayAgo, type.toString()))
                .weekCreatedContracts(contractRepository.countActiveByTime(weekAgo, type.toString()))
                .weekTerminatedContracts(contractRepository.countTerminatedByTime(weekAgo, type.toString()))
                .monthCreatedContracts(contractRepository.countActiveByTime(monthAgo, type.toString()))
                .montTerminatedContracts(contractRepository.countTerminatedByTime(monthAgo, type.toString()))
                .yearCreatedContracts(contractRepository.countActiveByTime(yearAgo, type.toString()))
                .yearTerminatedContracts(contractRepository.countTerminatedByTime(yearAgo, type.toString()))
                .build();
    }

    public List<ShortMeetings> getShortMeetings(int limit) {
        return meetingRepository.findByDateGreaterThanEqualOrderByDateAsc(System.currentTimeMillis(), PageRequest.of(0, limit))
                .stream()
                .map(meeting -> ShortMeetings
                        .builder()
                        .id(meeting.getId())
                        .date(meeting.getDate())
                        .type(meeting.getType())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public MainPageTable getMainPageTable(String token, int meetingLimit) {
        return MainPageTable
                .builder()
                .investmentsAmount(getInvestmentsAmount())
                .bidsCount(getBidCount())
                .pledgeCount(getPledgeCount())
                .investorsCount(getInvestorsCount(token))
                .agentContractsCounts(getContractsCounts(ContractType.AGENT))
                .investmentsContractsCounts(getContractsCounts(ContractType.CAR))
                .meetings(getShortMeetings(meetingLimit))
                .build();
    }

    public List<InvestmentQuery> getInvestmentQuery(String token) {
        List<Bid> bids = bidRepository.findByStatusAndType(BidStatus.CREATED.toString(), BidType.CAR.toString());
        List<SurnameWithInitials> surnameWithInitial = userSource.getSurnameWithInitials(token, SurnameRequest.builder().userIds(bids.stream().map(Bid::getUserId).toList()).needPhone(true).build());

        Map<String, SurnameWithInitials> surnameWithInitialMap = surnameWithInitial.stream()
                .collect(Collectors.toMap(SurnameWithInitials::getUserId, record -> record));

        return bids.stream()
                .map(bid -> {
                    SurnameWithInitials surnameRecord = surnameWithInitialMap.get(bid.getUserId());
                    return InvestmentQuery
                            .builder()
                            .date(new ObjectId(bid.getId()).getTimestamp())
                            .userId(bid.getUserId())
                            .bidId(bid.getId())
                            .amount(bid.getAmount())
                            .surnameWithInitials(surnameRecord != null ? surnameRecord.getSurnameWithInitials() : null)
                            .phoneNumber(surnameRecord != null ? surnameRecord.getPhone() : null)
                            .build();
                })
                .toList();
    }

    public List<InvestorsTable> getInvestorsTable(String token) {
        return userSource.getActiveInvestorInfo(token).stream()
                .map(info -> {
                    DocumentPhotoStatus docStatus = DocumentPhotoStatus.ACCEPTED;
                    List<DocumentPhoto> docs = documentPhotoRepository.findDocumentsByUserId(info.getUserId(), DocumentPhotoType.DOCUMENT);
                    if (!docs.isEmpty()) docStatus = DocumentPhotoStatus.MISSING;
                    if (docs.stream().anyMatch(documentPhoto -> documentPhoto.getStatus().equals(DocumentPhotoStatus.DENYIED))) docStatus = DocumentPhotoStatus.DENYIED;
                    if (!docStatus.equals(DocumentPhotoStatus.DENYIED))

                    TableBidStatus bidStatus = TableBidStatus.MISSING;
                    InvestorsTable.builder()
                            .activeContractCount(contractRepository.findContractsByUserId(info.getUserId(), ContractStatus.ACTIVE.toString()))
                            .bidStatus(bidStatus)
                            .birthday(info.getBirthday())
                            .investorType(info.getInvestorType())
                            .documentStatus(docStatus)
                            .phoneNumber(info.getPhoneNumber())
                            .surnameWithInitials(info.getSurnameWithInitials())
                            .build();
                })
                .toList;

    }

}

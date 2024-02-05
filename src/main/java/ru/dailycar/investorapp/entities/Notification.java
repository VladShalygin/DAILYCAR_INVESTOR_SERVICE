package ru.dailycar.investorapp.entities;

import org.springframework.lang.Nullable;

public class Notification {
    private String id;

    private String message;

    @Nullable
    private String subject;

    private SourceType type;

    private Long timestamp;

    private String creatorId;

    private String destinationId;

    private String destinationContact;

    private DestinationGroup destinationGroup;

    private ExecutionStatus status;
}

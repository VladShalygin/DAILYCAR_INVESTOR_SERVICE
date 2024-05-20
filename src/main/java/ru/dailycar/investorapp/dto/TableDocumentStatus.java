package ru.dailycar.investorapp.dto;

public enum TableDocumentStatus {
    MISSING, // НЕТ ДОКОВ
    SENT, // ДОКИ ОТПРАВЛЕНЫ
    ACCEPTED, // ДОКИ ПРИНЯТЫ
    REQUESTED_AGAIN, // ДОКИ ПЕРЕЗАПРОШЕНЫ
    DENIED // ОТКЛОНЕНЫ
}

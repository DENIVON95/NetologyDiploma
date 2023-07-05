package ru.netology.database;

import lombok.Data;

@Data
public class PaymentEntity {
    private String id;
    private String transactionId;
    private String amount;
    private String status;
    private String creationDate;

}

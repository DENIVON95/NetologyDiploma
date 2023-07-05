package ru.netology.database;

import lombok.Data;

@Data
public class CreditRequestEntity {
    private String id;
    private String bankId;
    private String status;
    private String creationDate;
}

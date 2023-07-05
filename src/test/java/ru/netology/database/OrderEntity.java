package ru.netology.database;

import lombok.Data;

@Data
public class OrderEntity {
    private String id;
    private String creditId;
    private String paymentId;
    private String creationDate;

}

package com.example.MtsTestExam.entity.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BankAccountDto {

    private UUID accountNumber;

    private String accountCurrency;

    private UUID clientId;
}

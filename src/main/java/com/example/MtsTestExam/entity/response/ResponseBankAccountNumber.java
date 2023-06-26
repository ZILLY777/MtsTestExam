package com.example.MtsTestExam.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ResponseBankAccountNumber {
    private UUID accountNumber;
}

package com.example.MtsTestExam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BankAccountNotFound extends RuntimeException{
    public BankAccountNotFound(){super("BANK_ACCOUNT_NOT_FOUND");}
}

package com.example.MtsTestExam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BankAccountNotFoundException extends RuntimeException{
    public BankAccountNotFoundException(){super("BANK_ACCOUNT_NOT_FOUND");}
}

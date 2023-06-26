package com.example.MtsTestExam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDocDataException extends RuntimeException{
    public InvalidDocDataException(){super("INVALID_DATA_IN_DOCUMENTS");}
}

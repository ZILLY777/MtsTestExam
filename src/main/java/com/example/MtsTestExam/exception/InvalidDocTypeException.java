package com.example.MtsTestExam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDocTypeException extends RuntimeException{
    public InvalidDocTypeException(){super("INVALID_TYPE_OF_DOCUMENTS");}
}

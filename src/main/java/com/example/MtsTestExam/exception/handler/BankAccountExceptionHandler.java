package com.example.MtsTestExam.exception.handler;

import com.example.MtsTestExam.entity.response.ResponseSubError;
import com.example.MtsTestExam.entity.response.generic.ResponseBasicError;
import com.example.MtsTestExam.exception.BankAccountNotFoundException;
import com.example.MtsTestExam.exception.InvalidCurrencyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankAccountExceptionHandler {

    @ExceptionHandler({InvalidCurrencyException.class})
    public ResponseEntity<Object> handleInvalidCurrency(Exception ex){
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Валюта введена неправильно либо не доступна"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BankAccountNotFoundException.class})
    public ResponseEntity<Object> handleBankAccountNotFound(Exception ex){
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Счет не найден"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}

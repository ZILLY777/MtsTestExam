package com.example.MtsTestExam.exception.handler;

import com.example.MtsTestExam.entity.response.ResponseSubError;
import com.example.MtsTestExam.entity.response.generic.ResponseBasicError;
import com.example.MtsTestExam.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler({EmptyFIOException.class})
    public ResponseEntity<Object> handleEmptyFIO(Exception ex){
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Поля 'surname' и 'name' не могут быть пустыми"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidDocTypeException.class})
    public ResponseEntity<Object> handleInvalidDocType(Exception ex){
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Неверный или недоступный тип документов"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ClientAlreadyExistException.class})
    public ResponseEntity<Object> handleAlreadyExistClient(Exception ex){
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Клиент уже зарегистрирован"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ClientNotFoundException.class})
    public ResponseEntity<Object> handleAlreadyNotFoundClient(Exception ex){
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Клиент не найден"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleNotReadableFormat(Exception ex){
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Неверный формат"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidDocDataException.class})
    public ResponseEntity<Object> handleIvalidData(Exception ex){
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Данные документа должны быть заполнены"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}

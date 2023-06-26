package com.example.MtsTestExam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientNotFound extends RuntimeException {
    public ClientNotFound(){super("CLIENT_NOT_FOUND");}
}

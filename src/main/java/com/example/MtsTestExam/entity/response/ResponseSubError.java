package com.example.MtsTestExam.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseSubError {
    private String code;
    private String message;
}

package com.example.MtsTestExam.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ClientDto {

    private UUID id;

    private String surname;

    private String name;

    private String patronymic;

    private String document;

    private String documentData;
    @NotNull(message = "Birthday cannot be null")
    @Past
    private LocalDate birthday;
}

package com.example.MtsTestExam.service;

import com.example.MtsTestExam.entity.dto.BankAccountDto;
import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.exception.*;
import com.example.MtsTestExam.service.serviceInterfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
public class ClientTest {

    @Autowired
    private ClientService clientService;

    @Test
    void testPostClientWithInvalidName(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        Exception exception = assertThrows(EmptyFIOException.class, () -> {
            clientService.save(clientDto);
        });
        String expectedMessage = "EMPTY_FIO";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostClientWithInvalidSurName(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        Exception exception = assertThrows(EmptyFIOException.class, () -> {
            clientService.save(clientDto);
        });
        String expectedMessage = "EMPTY_FIO";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostClientWithInvalidDocType(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        Exception exception = assertThrows(InvalidDocTypeException.class, () -> {
            clientService.save(clientDto);
        });
        String expectedMessage = "INVALID_TYPE_OF_DOCUMENTS";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostClientWithInvalidDocData(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        Exception exception = assertThrows(InvalidDocDataException.class, () -> {
            clientService.save(clientDto);
        });
        String expectedMessage = "INVALID_DATA_IN_DOCUMENTS";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostValidClient(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("9238774");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        assertEquals(clientService.save(clientDto).getClass(), UUID.class);
    }

    @Test
    void testGetAllClients(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        assertEquals(clientService.getAllClients().getClass(), ArrayList.class);
    }

    @Test
    void testGetAllEmptyClients(){
        assertEquals(clientService.getAllClients().getClass(), ArrayList.class);
    }

    @Test
    void testDeleteInvalidClient(){
        UUID uuid = UUID.randomUUID();
        Exception exception = assertThrows(ClientNotFound.class, () -> {
            clientService.deleteClient(uuid);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testDeleteClient(){
        UUID uuid = UUID.randomUUID();
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        UUID id =clientService.save(clientDto);
        clientService.deleteClient(id);
        Exception exception = assertThrows(ClientNotFound.class, () -> {
            clientService.deleteClient(uuid);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

}

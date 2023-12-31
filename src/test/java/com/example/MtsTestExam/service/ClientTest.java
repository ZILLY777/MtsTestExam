package com.example.MtsTestExam.service;

import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.exception.*;
import com.example.MtsTestExam.service.serviceInterfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    private ClientDto clientDto;

    void createClientDTO(){
        clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
    }
    void createClientDTOSecond(){
        clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("7234789234");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
    }

    @Test
    void testPostClientWithInvalidName(){
        createClientDTO();
        clientDto.setName("");
        Exception exception = assertThrows(EmptyFIOException.class, () -> {
            clientService.postClient(clientDto);
        });
        String expectedMessage = "EMPTY_FIO";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostClientWithInvalidSurName(){
        createClientDTO();
        clientDto.setSurname("");
        Exception exception = assertThrows(EmptyFIOException.class, () -> {
            clientService.postClient(clientDto);
        });
        String expectedMessage = "EMPTY_FIO";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostClientWithInvalidDocType(){
        createClientDTO();
        clientDto.setDocument("");
        Exception exception = assertThrows(InvalidDocTypeException.class, () -> {
            clientService.postClient(clientDto);
        });
        String expectedMessage = "INVALID_TYPE_OF_DOCUMENTS";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostClientWithInvalidDocData(){
        createClientDTO();
        clientDto.setDocumentData("");
        Exception exception = assertThrows(InvalidDocDataException.class, () -> {
            clientService.postClient(clientDto);
        });
        String expectedMessage = "INVALID_DATA_IN_DOCUMENTS";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostValidClient(){
        createClientDTO();
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        assertEquals(clientService.postClient(clientDto).getClass(), UUID.class);
    }

    @Test
    void testGetAllClients(){
        createClientDTO();
        createClientDTOSecond();
        assertEquals(clientService.getAllClients().getClass(), ArrayList.class);
    }

    @Test
    void testGetAllEmptyClients(){
        assertEquals(clientService.getAllClients().getClass(), ArrayList.class);
    }

    @Test
    void testDeleteInvalidClient(){
        UUID uuid = UUID.randomUUID();
        Exception exception = assertThrows(ClientNotFoundException.class, () -> {
            clientService.deleteClient(uuid);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testDeleteClient(){
        createClientDTO();
        UUID id =clientService.postClient(clientDto);
        clientService.deleteClient(id);
        Exception exception = assertThrows(ClientNotFoundException.class, () -> {
            clientService.deleteClient(id);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

}

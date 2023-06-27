package com.example.MtsTestExam.service;

import com.example.MtsTestExam.entity.dto.BankAccountDto;
import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.exception.BankAccountNotFoundException;
import com.example.MtsTestExam.exception.ClientNotFoundException;
import com.example.MtsTestExam.exception.InvalidCurrencyException;
import com.example.MtsTestExam.service.serviceInterfaces.BankAccountService;
import com.example.MtsTestExam.service.serviceInterfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
public class BankAccountTest {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private ClientService clientService;

    private ClientDto clientDto;

    private BankAccountDto bankAccountDto;

    void createClientDTO(){
        clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
    }

    void createBankAccountDto(){
        bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountCurrency("rub");
    }
    @Test
    void testPostEmptyBankAccount(){
        BankAccountDto bankAccountDto = new BankAccountDto();
        Exception exception = assertThrows(InvalidCurrencyException.class, () -> {
            bankAccountService.postBankAccount(bankAccountDto);
        });
        String expectedMessage = "INVALID_CURRENCY";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostInvalidCurrency(){
        UUID uuid = UUID.randomUUID();
        createBankAccountDto();
        bankAccountDto.setAccountCurrency("RUB");
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(InvalidCurrencyException.class, () -> {
            bankAccountService.postBankAccount(bankAccountDto);
        });
        String expectedMessage = "INVALID_CURRENCY";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostInvalidUserId(){
        UUID uuid = UUID.randomUUID();
        createBankAccountDto();
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(ClientNotFoundException.class, () -> {
            bankAccountService.postBankAccount(bankAccountDto);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testNormalPost(){
        createClientDTO();
        UUID uuid = clientService.postClient(clientDto);
        createBankAccountDto();
        bankAccountDto.setClientId(uuid);
        assertEquals(bankAccountService.postBankAccount(bankAccountDto).getClass(), UUID.class);
    }

    @Test
    void testGetInvalidUserIdListOfAcc(){
        UUID uuid = UUID.randomUUID();
        createBankAccountDto();
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(ClientNotFoundException.class, () -> {
            bankAccountService.getClientListOfBankAccounts(uuid);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testGetListOfAcc(){
        createClientDTO();
        UUID uuid = clientService.postClient(clientDto);
        createBankAccountDto();
        bankAccountDto.setClientId(uuid);
        assertEquals(bankAccountService.getClientListOfBankAccounts(uuid).getClass(), ArrayList.class);
    }

    @Test
    void testDeleteInvalidUserId(){
        UUID uuid = UUID.randomUUID();
        UUID uuidTwo = UUID.randomUUID();
        createBankAccountDto();
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(ClientNotFoundException.class, () -> {
            bankAccountService.deleteBankAccount(uuid, uuidTwo);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testDeleteInvalidBankAccountId(){
        createClientDTO();
        UUID uuid = clientService.postClient(clientDto);
        UUID uuidTwo = UUID.randomUUID();
        createBankAccountDto();
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(BankAccountNotFoundException.class, () -> {
            bankAccountService.deleteBankAccount(uuid, uuidTwo);
        });
        String expectedMessage = "BANK_ACCOUNT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testDeleteValidBankAccountId(){
        createClientDTO();
        UUID uuid = clientService.postClient(clientDto);
        createBankAccountDto();
        bankAccountDto.setClientId(uuid);
        UUID uuidTwo =bankAccountService.postBankAccount(bankAccountDto);
        bankAccountService.deleteBankAccount(uuid, uuidTwo);
        Exception exception = assertThrows(BankAccountNotFoundException.class, () -> {
            bankAccountService.deleteBankAccount(uuid, uuidTwo);
        });
        String expectedMessage = "BANK_ACCOUNT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

    }
}

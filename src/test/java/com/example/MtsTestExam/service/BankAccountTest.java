package com.example.MtsTestExam.service;

import com.example.MtsTestExam.entity.dto.BankAccountDto;
import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.exception.BankAccountNotFound;
import com.example.MtsTestExam.exception.ClientNotFound;
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
import java.util.List;
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

    @Test
    void testPostEmptyBankAccount(){
        BankAccountDto bankAccountDto = new BankAccountDto();
        Exception exception = assertThrows(InvalidCurrencyException.class, () -> {
            bankAccountService.save(bankAccountDto);
        });
        String expectedMessage = "INVALID_CURRENCY";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostInvalidCurrency(){
        UUID uuid = UUID.randomUUID();
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountCurrency("RUB");
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(InvalidCurrencyException.class, () -> {
            bankAccountService.save(bankAccountDto);
        });
        String expectedMessage = "INVALID_CURRENCY";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testPostInvalidUserId(){
        UUID uuid = UUID.randomUUID();
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountCurrency("rub");
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(ClientNotFound.class, () -> {
            bankAccountService.save(bankAccountDto);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testNormalPost(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        UUID uuid = clientService.save(clientDto);
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountCurrency("rub");
        bankAccountDto.setClientId(uuid);
        assertEquals(bankAccountService.save(bankAccountDto).getClass(), UUID.class);
    }

    @Test
    void testGetInvalidUserIdListOfAcc(){
        UUID uuid = UUID.randomUUID();
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountCurrency("rub");
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(ClientNotFound.class, () -> {
            bankAccountService.getClientListOfBankAccounts(uuid);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testGetListOfAcc(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        UUID uuid = clientService.save(clientDto);
        BankAccountDto bankAccountDto = new BankAccountDto();
        BankAccountDto bankAccountDtoTwo = new BankAccountDto();
        bankAccountDto.setAccountCurrency("rub");
        bankAccountDto.setClientId(uuid);
        bankAccountDtoTwo.setAccountCurrency("usd");
        bankAccountDtoTwo.setClientId(uuid);
        assertEquals(bankAccountService.getClientListOfBankAccounts(uuid).getClass(), ArrayList.class);
    }

    @Test
    void testDeleteInvalidUserId(){
        UUID uuid = UUID.randomUUID();
        UUID uuidTwo = UUID.randomUUID();
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountCurrency("rub");
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(ClientNotFound.class, () -> {
            bankAccountService.deleteBankAccount(uuid, uuidTwo);
        });
        String expectedMessage = "CLIENT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testDeleteInvalidBankAccountId(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        UUID uuid = clientService.save(clientDto);
        UUID uuidTwo = UUID.randomUUID();
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountCurrency("rub");
        bankAccountDto.setClientId(uuid);
        Exception exception = assertThrows(BankAccountNotFound.class, () -> {
            bankAccountService.deleteBankAccount(uuid, uuidTwo);
        });
        String expectedMessage = "BANK_ACCOUNT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testDeleteValidBankAccountId(){
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        UUID uuid = clientService.save(clientDto);
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountCurrency("rub");
        bankAccountDto.setClientId(uuid);
        UUID uuidTwo =bankAccountService.save(bankAccountDto);
        bankAccountService.deleteBankAccount(uuid, uuidTwo);
        Exception exception = assertThrows(BankAccountNotFound.class, () -> {
            bankAccountService.deleteBankAccount(uuid, uuidTwo);
        });
        String expectedMessage = "BANK_ACCOUNT_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

    }
}

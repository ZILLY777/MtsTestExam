package com.example.MtsTestExam.controller;

import com.example.MtsTestExam.entity.dto.BankAccountDto;
import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.entity.response.ResponseBankAccountNumber;
import com.example.MtsTestExam.entity.response.ResponseClientId;
import com.example.MtsTestExam.entity.response.ResponseClients;
import com.example.MtsTestExam.entity.response.ResponseUserListOfBankAccounts;
import com.example.MtsTestExam.entity.response.generic.ResponseData;
import com.example.MtsTestExam.service.serviceInterfaces.BankAccountService;
import com.example.MtsTestExam.service.serviceInterfaces.ClientService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final BankAccountService bankAccountService;

    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @PostMapping
    public ResponseEntity<ResponseData<ResponseClientId>> postClient(@RequestBody @Valid ClientDto clientDto){
        ResponseData<ResponseClientId> data = ResponseData.wrap(new ResponseClientId(clientService.postClient(clientDto)));
        logger.info("Клиент зарегистрирован {}", data.getData());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/all-clients")
    public ResponseEntity<ResponseData<ResponseClients>> getAllClients(){
        ResponseData<ResponseClients> data = ResponseData.wrap(new ResponseClients(clientService.getAllClients()));
        logger.info("Возвращен список {}", data.getData());
        return ResponseEntity.ok(data);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteClient(@RequestParam UUID clientId){
        clientService.deleteClient(clientId);
        logger.info("Аккаунт clientId={} удален", clientId);
        return ResponseEntity.ok("");
    }

    @PostMapping("/bank-account")
    public ResponseEntity<ResponseData<ResponseBankAccountNumber>> postBankAccount(@RequestBody BankAccountDto bankAccountDto){
        ResponseData<ResponseBankAccountNumber> data = ResponseData.wrap(new ResponseBankAccountNumber(bankAccountService.postBankAccount(bankAccountDto)));
        logger.info("Счет зарегистрирован ={}", data.getData());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/bank-account/all-user")
    public ResponseEntity<ResponseData<ResponseUserListOfBankAccounts>> getClientListOfBankAccount(@RequestParam UUID clientId){
        ResponseData<ResponseUserListOfBankAccounts> data = ResponseData
                .wrap(new ResponseUserListOfBankAccounts(bankAccountService.getClientListOfBankAccounts(clientId)));
        logger.info("Выдан список счетов {} clientId={} ", data.getData(), clientId);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/bank-account")
    public ResponseEntity<?> deleteClient(@RequestParam UUID clientId, @RequestParam UUID bankAccountNumber ){
        bankAccountService.deleteBankAccount(clientId, bankAccountNumber);
        logger.info("Счет bankAccountNumber={} удален", bankAccountNumber);
        return ResponseEntity.ok("");
    }

}

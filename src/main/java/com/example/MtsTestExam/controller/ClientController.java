package com.example.MtsTestExam.controller;

import com.example.MtsTestExam.entity.dto.BankAccountDto;
import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.entity.response.ResponseBankAccountNumber;
import com.example.MtsTestExam.entity.response.ResponseClientId;
import com.example.MtsTestExam.entity.response.ResponseClients;
import com.example.MtsTestExam.entity.response.ResponseUserBankAccountsList;
import com.example.MtsTestExam.entity.response.generic.ResponseData;
import com.example.MtsTestExam.service.serviceInterfaces.BankAccountService;
import com.example.MtsTestExam.service.serviceInterfaces.ClientService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
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
        ResponseData<ResponseClientId> data = ResponseData.wrap(new ResponseClientId(clientService.save(clientDto)));
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/all-clients")
    public ResponseEntity<ResponseData<ResponseClients>> getAllClients(){
        ResponseData<ResponseClients> data = ResponseData.wrap(new ResponseClients(clientService.getAllClients()));
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteClient(@RequestParam UUID id){
        clientService.deleteClient(id);
        logger.info("Ok");
        return ResponseEntity.ok("");
    }

    @PostMapping("/bank-account")
    public ResponseEntity<ResponseData<ResponseBankAccountNumber>> postBankAccount(@RequestBody BankAccountDto bankAccountDto){
        ResponseData<ResponseBankAccountNumber> data = ResponseData.wrap(new ResponseBankAccountNumber(bankAccountService.save(bankAccountDto)));
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/bank-account/all-user")
    public ResponseEntity<ResponseData<ResponseUserBankAccountsList>> getClientListOfBankAccount(@RequestParam UUID clientId){
        ResponseData<ResponseUserBankAccountsList> data = ResponseData
                .wrap(new ResponseUserBankAccountsList(bankAccountService.getClientListOfBankAccounts(clientId)));
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/bank-account")
    public ResponseEntity<?> deleteClient(@RequestParam UUID clientId, @RequestParam UUID bankAccountNumber ){
        bankAccountService.deleteBankAccount(clientId, bankAccountNumber);
        return ResponseEntity.ok("");
    }

}

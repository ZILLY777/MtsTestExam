package com.example.MtsTestExam.service.serviceImpl;

import com.example.MtsTestExam.controller.ClientController;
import com.example.MtsTestExam.entity.dto.BankAccountDto;
import com.example.MtsTestExam.exception.BankAccountNotFoundException;
import com.example.MtsTestExam.exception.ClientNotFoundException;
import com.example.MtsTestExam.exception.InvalidCurrencyException;
import com.example.MtsTestExam.mapper.BankAccountMapper;
import com.example.MtsTestExam.repository.repositoryInterface.BankAccountRepository;
import com.example.MtsTestExam.repository.repositoryInterface.ClientRepository;
import com.example.MtsTestExam.service.serviceInterfaces.BankAccountService;
import lombok.RequiredArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;
    private final BankAccountMapper bankAccountMapper;
   @Value("#{'${currency}'.split(',')}")
    private List<String> currencyList;
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);


    @Override
    public UUID postBankAccount(BankAccountDto bankAccountDto) {
        if(!currencyList.contains(bankAccountDto.getAccountCurrency())){
            throw new InvalidCurrencyException();
        }
        if(clientRepository.findClientById(bankAccountDto.getClientId()).isEmpty()){
            throw new ClientNotFoundException();
        }
        return bankAccountRepository.save(bankAccountMapper.bankAccountDtoToBankAccount(bankAccountDto)).getAccountNumber();
    }

    @Override
    public List<BankAccountDto> getClientListOfBankAccounts(UUID clientId) {
        if(clientRepository.findClientById(clientId).isEmpty()){
            logger.info("Client not found");//уточнить
            throw new ClientNotFoundException();
        }
        return bankAccountMapper.bankAccountListToBankAccountListDto(bankAccountRepository.findBankAccountsByClientId(clientId));
    }

    @Override
    public void deleteBankAccount(UUID clientId, UUID bankAccountNumber) {
        if(clientRepository.findClientById(clientId).isEmpty()){

            throw new ClientNotFoundException();
        }
        if(bankAccountRepository.findBankAccountByAccountNumber(bankAccountNumber).isEmpty()){
            throw new BankAccountNotFoundException();
        }
        bankAccountRepository.deleteBankAccountByClientIdAndBankAccountId(clientId, bankAccountNumber);

    }
}

package com.example.MtsTestExam.service.serviceInterfaces;

import com.example.MtsTestExam.entity.dto.BankAccountDto;

import java.util.List;
import java.util.UUID;

public interface BankAccountService {
    UUID save(BankAccountDto bankAccountDto);
    List<BankAccountDto> getClientListOfBankAccounts(UUID clientId);
    void deleteBankAccount(UUID clientId, UUID bankAccountId);
}

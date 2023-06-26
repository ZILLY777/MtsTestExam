package com.example.MtsTestExam.repository.repositoryInterface;

import com.example.MtsTestExam.entity.tables.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository {
    BankAccount save(BankAccount bankAccount);
    List<BankAccount> findBankAccountsByClientId(UUID clientId);
    void deleteBankAccountByClientIdAndBankAccountId(UUID clientId, UUID bankAccountId);

    Optional<BankAccount> findBankAccountByAccountNumber(UUID accountNumber);
}

package com.example.MtsTestExam.mapper;

import com.example.MtsTestExam.entity.dto.BankAccountDto;
import com.example.MtsTestExam.entity.tables.BankAccount;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountDto bankAccountToBankAccountDto(BankAccount bankAccount);
    BankAccount bankAccountDtoToBankAccount(BankAccountDto bankAccountDto);
    List<BankAccountDto> bankAccountListToBankAccountListDto(List<BankAccount> list);
    List<BankAccount> bankAccountListDtoToBankAccountList(List<BankAccountDto> list);
}

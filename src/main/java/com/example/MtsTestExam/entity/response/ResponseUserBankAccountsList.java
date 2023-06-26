package com.example.MtsTestExam.entity.response;

import com.example.MtsTestExam.entity.dto.BankAccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseUserBankAccountsList {
    private List<BankAccountDto> userBankAccounts;
}

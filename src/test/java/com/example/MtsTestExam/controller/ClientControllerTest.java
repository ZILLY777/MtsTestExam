package com.example.MtsTestExam.controller;

import com.example.MtsTestExam.entity.dto.BankAccountDto;
import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.repository.repositoryInterface.BankAccountRepository;
import com.example.MtsTestExam.repository.repositoryInterface.ClientRepository;
import com.example.MtsTestExam.service.serviceInterfaces.BankAccountService;
import com.example.MtsTestExam.service.serviceInterfaces.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientService clientService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper mapper;

    private static String uuid;
    private static String accountNumber;


    @Test
    public void postClient() throws Exception {
        ClientDto clientDto = new ClientDto();
        clientDto.setSurname("Alekseev");
        clientDto.setName("Alex");
        clientDto.setPatronymic("Alekseevich");
        clientDto.setDocument("passport");
        clientDto.setDocumentData("52347");
        clientDto.setBirthday(LocalDate.of(2023, 5, 3));
        MvcResult mvcResult = this.mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json;charset=UTF-8"))
                .andReturn();

        uuid = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.data.clientId");
    }

    @Test
    public void getAllClients() throws Exception {
        postClient();
        String str = mapper.writeValueAsString(clientService.getAllClients());
        MvcResult result =mockMvc.perform(get("/client/all-clients")).andReturn();
        Assertions.assertEquals(JsonPath.parse(result.getResponse().getContentAsString()).read("$.data.clients").toString(), str);
    }

    @Test
    public void deleteClient() throws Exception {
        postClient();
        this.mockMvc.perform(delete("/client").param("id", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andReturn();
    }

    @Test
    public void postBankAccount() throws Exception {
        postClient();
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountCurrency("usd");
        bankAccountDto.setClientId(UUID.fromString(uuid));
        MvcResult mvcResult = this.mockMvc.perform(post("/client/bank-account")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(bankAccountDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json;charset=UTF-8"))
                .andReturn();
        accountNumber = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.data.accountNumber");
    }

    @Test
    public void getClientListOfBankAccount() throws Exception{
        postBankAccount();
        String str = mapper.writeValueAsString(bankAccountService.getClientListOfBankAccounts(UUID.fromString(uuid)));
        MvcResult result =mockMvc.perform(get("/client/bank-account/all-user").param("clientId", uuid)).andReturn();
        Assertions.assertEquals(JsonPath.parse(result.getResponse().getContentAsString()).read("$.data.userBankAccounts").toString(), str);

    }

    @Test
    public void deleteBankAccount() throws Exception{
        postBankAccount();
        this.mockMvc.perform(delete("/client/bank-account").param("clientId", uuid).param("bankAccountNumber", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andReturn();

    }



}

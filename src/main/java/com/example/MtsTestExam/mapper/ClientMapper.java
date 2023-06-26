package com.example.MtsTestExam.mapper;

import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.entity.tables.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto clientToClientAccountDto(Client bankAccount);
    Client clientDtoToClient(ClientDto bankAccountDto);
    List<ClientDto> clientListToClientListDto(List<Client> list);
    List<Client> clientListDtoToClientList(List<ClientDto> list);
}

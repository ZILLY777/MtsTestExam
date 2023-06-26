package com.example.MtsTestExam.service.serviceInterfaces;

import com.example.MtsTestExam.entity.dto.ClientDto;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    UUID save(ClientDto clientDto);
    List<ClientDto> getAllClients();
    void deleteClient(UUID id);
}

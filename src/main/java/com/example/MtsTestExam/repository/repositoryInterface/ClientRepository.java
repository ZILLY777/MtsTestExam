package com.example.MtsTestExam.repository.repositoryInterface;

import com.example.MtsTestExam.entity.tables.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    Client save(Client client);
    List<Client> findAllClient();
    void deleteClient(UUID id);
    Optional<Client> findClientByDocumentTypeAndData(String type, String data);
    Optional<Client> findClientById(UUID id);

}

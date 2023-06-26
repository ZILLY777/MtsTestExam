package com.example.MtsTestExam.service.serviceImpl;

import com.example.MtsTestExam.controller.ClientController;
import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.entity.tables.Client;
import com.example.MtsTestExam.exception.*;
import com.example.MtsTestExam.mapper.ClientMapper;
import com.example.MtsTestExam.repository.repositoryInterface.ClientRepository;
import com.example.MtsTestExam.service.serviceInterfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    @Value("#{'${documentType}'.split(',')}")
    private List<String> documentTypeList;
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Override
    public UUID postClient(ClientDto clientDto) {
        if(clientDto.getName().isEmpty() || clientDto.getSurname().isEmpty() ){
            logger.warn("Фамилия или Имя введены неправильно {} {}", clientDto.getSurname(), clientDto.getName());
            throw new EmptyFIOException();
        }
        if(!documentTypeList.contains(clientDto.getDocument())){
            logger.warn("Тип документа не установлен {}", clientDto.getDocument());
            throw new InvalidDocTypeException();
        }
        if(clientDto.getDocumentData().isEmpty()){
            logger.warn("Данные документа не верны {}", clientDto.getDocumentData());
            throw new InvalidDocDataException();
        }
        Optional<Client> client= clientRepository.findClientByDocumentTypeAndData(clientDto.getDocument(), clientDto.getDocumentData());
        if(client.isPresent()){
            logger.warn("Клиент уже зарегистрирован {}", client);
            throw new ClientAlreadyExistException();
        }
        logger.info("Клиент зарегистрирован {}", clientDto);
        return clientRepository.save(clientMapper.clientDtoToClient(clientDto)).getId();
    }

    @Override
    public List<ClientDto> getAllClients() {
        logger.info("Выдан список клиентов");
        return clientMapper.clientListToClientListDto(clientRepository.findAllClient());
    }

    @Override
    public void deleteClient(UUID clientId) {
        Optional<Client> client= clientRepository.findClientById(clientId);
        if(client.isPresent()){
            clientRepository.deleteClient(clientId);
        }else{
            logger.error("Клиент не найден clientId = {}", clientId);
            throw new ClientNotFoundException();
        } //можно подумать над проверкой точно ли удалено

    }
}

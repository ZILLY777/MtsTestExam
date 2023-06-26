package com.example.MtsTestExam.service.serviceImpl;

import com.example.MtsTestExam.entity.dto.ClientDto;
import com.example.MtsTestExam.entity.tables.Client;
import com.example.MtsTestExam.exception.*;
import com.example.MtsTestExam.mapper.ClientMapper;
import com.example.MtsTestExam.repository.repositoryInterface.ClientRepository;
import com.example.MtsTestExam.service.serviceInterfaces.ClientService;
import lombok.RequiredArgsConstructor;
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

    @Override
    public UUID postClient(ClientDto clientDto) {
        if(clientDto.getName().isEmpty() || clientDto.getSurname().isEmpty() ){
            throw new EmptyFIOException();
        }
        if(!documentTypeList.contains(clientDto.getDocument())){
            throw new InvalidDocTypeException();
        }
        if(clientDto.getDocumentData().isEmpty()){
            throw new InvalidDocDataException();
        }
        Optional<Client> client= clientRepository.findClientByDocumentTypeAndData(clientDto.getDocument(), clientDto.getDocumentData());
        if(client.isPresent()){
            throw new ClientAlreadyExistException();
        }
        return clientRepository.save(clientMapper.clientDtoToClient(clientDto)).getId();
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientMapper.clientListToClientListDto(clientRepository.findAllClient());
    }

    @Override
    public void deleteClient(UUID clientId) {
        Optional<Client> client= clientRepository.findClientById(clientId);
        if(client.isPresent()){
            clientRepository.deleteClient(clientId);
        }else{
            throw new ClientNotFoundException();
        } //можно подумать над проверкой точно ли удалено

    }
}

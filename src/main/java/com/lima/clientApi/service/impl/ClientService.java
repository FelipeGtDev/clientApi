package com.lima.clientApi.service.impl;

import com.lima.clientApi.exceptions.ResourceNotFoundException;
import com.lima.clientApi.model.Client;
import com.lima.clientApi.model.Email;
import com.lima.clientApi.model.Phone;
import com.lima.clientApi.model.dto.ClientDTO;
import com.lima.clientApi.model.dto.EmailDTO;
import com.lima.clientApi.model.dto.PhoneDTO;
import com.lima.clientApi.repository.ClientRepository;
import com.lima.clientApi.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ClientService implements IClientService {

    public ClientRepository repository;


    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }


    @Override
    public Optional<ClientDTO> save(ClientDTO request) {

        Client client = convertDtoToClient(request);
        log.info("Start save client: {}", client);
        Optional<Client> optResponse = Optional.of(repository.save(client));
        ClientDTO response = builderClientDto(optResponse.get());

        return Optional.ofNullable(response);
    }

    public Page<ClientDTO> listAll(Pageable page) {
        Page<Client> clients = repository.listAll(page);

        return clients.map(this::builderClientDto);

    }

    @Override
    public Page<ClientDTO> listByAreaCode(Pageable page, String areaCode) {
        Page<Client> clients = repository.listByAreaCode(page, areaCode);

        return clients.map(this::builderClientDto);
    }

    public Page<ClientDTO> listByName(Pageable page, String name) {
        Page<Client> clients = repository.listByName(page, name);

        return clients.map(this::builderClientDto);
    }

    public Optional<ClientDTO> findById(Long id) {
        log.info("Start find client by id: {}", id);
        Optional<Client> client = repository.findById(id);
        if (client.isEmpty()) {
            log.warn("Client not found: {}", id);
            throw new ResourceNotFoundException("Cliente não encontrado");
        } else {
            return client.map(this::builderClientDto);
        }
    }

    public Optional<ClientDTO> update(Long id, ClientDTO request){
        Optional<Client> client = repository.findById(id);
        if (client.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        } else {
            log.info("Start update client: {}", client);
            Client clientUpdate = convertDtoToClient(request);
            clientUpdate.setId(id);
            repository.save(clientUpdate);
            log.info("Client updated: {}", clientUpdate);
            return client.map(this::builderClientDto);
        }
    }
    public void deleteById(Long id) {
        Optional<Client> client = repository.findById(id);
        if (client.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        } else {
            repository.deleteById(id);
            log.info("Client deleted: {}", client);
        }
    }

    private ClientDTO builderClientDto(Client client) {
        log.info("Start builderClientDTO: {}", client);
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .cpf(client.getCpf())
                .phones(builderPhoneDto(client.getPhones()))
                .emails(builderEmailDto(client.getEmails()))
                .build();
    }


    private List<EmailDTO> builderEmailDto(List<Email> emails) {
        List<EmailDTO> emailsDTO = new ArrayList<>();
        for (Email email : emails) {
            emailsDTO.add(EmailDTO.builder()
                    .id(email.getId())
                    .email(email.getEmail())
                    .build());
        }
        return emailsDTO;
    }


    private List<PhoneDTO> builderPhoneDto(List<Phone> phones) {
        List<PhoneDTO> phonesDTO = new ArrayList<>();
        for (Phone phone : phones) {
            phonesDTO.add(PhoneDTO.builder()
                    .id(phone.getId())
                    .ddd(phone.getDdd())
                    .number(phone.getNumber())
                    .build());
        }
        return phonesDTO;
    }


    private Client convertDtoToClient(ClientDTO request) {
        log.info("Start convertDtoToClient: {}", request);
        Client client = new Client();
        client.setId(request.getId());
        client.setName(request.getName());
        client.setCpf(request.getCpf());
        request.getPhones().forEach(phone -> client.getPhones().add(new Phone(phone, client)));
        request.getEmails().forEach(email -> client.getEmails().add(new Email(email, client)));
        return client;
    }
}

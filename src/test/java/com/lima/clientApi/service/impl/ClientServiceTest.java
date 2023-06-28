package com.lima.clientApi.service.impl;


import com.lima.clientApi.exceptions.ResourceNotFoundException;
import com.lima.clientApi.model.Address;
import com.lima.clientApi.model.Client;
import com.lima.clientApi.model.Email;
import com.lima.clientApi.model.Phone;
import com.lima.clientApi.model.dto.AddressDTO;
import com.lima.clientApi.model.dto.ClientDTO;
import com.lima.clientApi.model.dto.EmailDTO;
import com.lima.clientApi.model.dto.PhoneDTO;
import com.lima.clientApi.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    private ClientService service;

    //Constants
    private static final Address ADDRESS = new Address(1L, "Rua sucupira", "123", "Casa", "Jardim", "São Paulo", "SP", "12345678", new Client());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ClientService(repository);
    }

    @Test
    void save_ShouldReturnSavedClientDTO() {



        // Dados de entrada
        ClientDTO request = createClientDTO();

        // Mock do repositório

        Client client = createClient(1L, "123456789", "John Doe", 1L, "11", "12345678",
                2L, "21", "92345678", 1L, "teste@teste.com");

        when(repository.save(any(Client.class))).thenReturn(client);

        // Executar o método save
        Optional<ClientDTO> response = service.save(request);

        // Verificações
        assertEquals(client.getId(), response.get().getId());
        assertEquals(client.getName(), response.get().getName());
        assertEquals(client.getCpf(), response.get().getCpf());
        assertEquals(client.getPhones().size(), response.get().getPhones().size());
        assertEquals(client.getPhones().get(0).getId(), response.get().getPhones().get(0).getId());
        assertEquals(client.getPhones().get(0).getDdd(), response.get().getPhones().get(0).getDdd());
        assertEquals(client.getPhones().get(0).getNumber(), response.get().getPhones().get(0).getNumber());
        assertEquals(client.getPhones().get(1).getId(), response.get().getPhones().get(1).getId());
        assertEquals(client.getPhones().get(1).getDdd(), response.get().getPhones().get(1).getDdd());
        assertEquals(client.getPhones().get(1).getNumber(), response.get().getPhones().get(1).getNumber());
        assertEquals(client.getEmails().size(), response.get().getEmails().size());
        assertEquals(client.getEmails().get(0).getId(), response.get().getEmails().get(0).getId());
        assertEquals(client.getEmails().get(0).getEmail(), response.get().getEmails().get(0).getEmail());
        assertEquals(client.getAddress().getCity(), response.get().getAddress().getCity());
        assertEquals(client.getAddress().getComplement(), response.get().getAddress().getComplement());
        assertEquals(client.getAddress().getNeighborhood(), response.get().getAddress().getNeighborhood());
        assertEquals(client.getAddress().getNumber(), response.get().getAddress().getNumber());
        assertEquals(client.getAddress().getState(), response.get().getAddress().getState());
        assertEquals(client.getAddress().getStreet(), response.get().getAddress().getStreet());
        assertEquals(client.getAddress().getZipCode(), response.get().getAddress().getZipCode());


        verify(repository, times(1)).save(any(Client.class));
    }

    @Test
    void listAll_ShouldReturnListOfClientsDTO() {
        // Dados de entrada
        Pageable pageable = PageRequest.of(0, 10);

        // Mock do repositório
        List<Client> clients = new ArrayList<>();

        Client client1 = createClient(1L, "123456789", "John Doe", 1L, "11", "12345678",
                2L, "21", "92345678", 1L, "teste@teste.com");
        Client client2 = createClient(2L, "987654321", "Jane Smith", 3L, "32", "12345678",
                4L, "21", "92345678", 2L, "teste@gmail.com");

        clients.add(client1);
        clients.add(client2);
        Page<Client> clientPage = new PageImpl<>(clients, pageable, clients.size());

        when(repository.listAll(pageable)).thenReturn(clientPage);

        // Executar o método listAll
        Page<ClientDTO> response = service.listAll(pageable);

        // Verificações
        assertEquals(clients.size(), response.getTotalElements());
        assertEquals(clients.size(), response.getContent().size());
        assertEquals(client1.getId(), response.getContent().get(0).getId());
        assertEquals(client1.getName(), response.getContent().get(0).getName());
        assertEquals(client1.getCpf(), response.getContent().get(0).getCpf());
        assertTrue(response.getContent().get(0).getPhones().size() > 0);
        assertTrue(response.getContent().get(0).getEmails().size() > 0);
        assertNotNull(response.getContent().get(0).getAddress());

        assertEquals(client2.getId(), response.getContent().get(1).getId());
        assertEquals(client2.getName(), response.getContent().get(1).getName());
        assertEquals(client2.getCpf(), response.getContent().get(1).getCpf());
        assertNotNull(response.getContent().get(1).getAddress());
        verify(repository, times(1)).listAll(pageable);
    }

    @Test
    void listByAreaCode_ShouldReturnListOfClientsWhitTheSameAreaCodeRequested() {
        // Dados de entrada
        Pageable pageable = PageRequest.of(0, 10);
        String areaCode = "32";

        // Mock do repositório
        List<Client> clients = new ArrayList<>();

        Client client = createClient(2L, "987654321", "Jane Smith", 3L, "32", "12345678",
                4L, "21", "92345678", 2L, "teste@gmail.com");

        clients.add(client);
        Page<Client> clientPage = new PageImpl<>(clients, pageable, clients.size());
        when(repository.listByAreaCode(pageable, areaCode)).thenReturn(clientPage);

        // Executar o método listByAreaCode
        Page<ClientDTO> response = service.listByAreaCode(pageable, areaCode);

        // Verificações
        assertEquals(clients.size(), response.getTotalElements());
        assertEquals(clients.size(), response.getContent().size());
        assertEquals(client.getId(), response.getContent().get(0).getId());
        assertEquals(client.getName(), response.getContent().get(0).getName());
        assertEquals(client.getCpf(), response.getContent().get(0).getCpf());
        assertEquals(client.getPhones().size(), response.getContent().get(0).getPhones().size());
        assertEquals(areaCode, response.getContent().get(0).getPhones().get(0).getDdd());
        assertNotEquals("21", client.getPhones().get(0).getDdd());

//        assertEquals(areaCode, response.getContent().get(0).getPhones().stream().filter(phone -> phone.getDdd().equals(areaCode)));
        verify(repository, times(1)).listByAreaCode(pageable, areaCode);

    }

    @Test
    void listByName_ShouldReturnListOfClientsWhitThePartOfNameRequested() {
        // Dados de entrada
        Pageable pageable = PageRequest.of(0, 10);
        String name = "jan";

        // Mock do repositório
        List<Client> clients = new ArrayList<>();

        Client client = createClient(2L, "987654321", "Jane Smith", 3L, "32", "12345678",
                4L, "21", "92345678", 2L, "teste@gmail.com");

        Client client2 = createClient(2L, "987654321", "Janaina Dark", 4L, "21", "12345678",
                10L, "11", "92345678", 8L, "teste2@gmail.com");

        clients.add(client);
        clients.add(client2);
        Page<Client> clientPage = new PageImpl<>(clients, pageable, clients.size());
        when(repository.listByName(pageable, name)).thenReturn(clientPage);

        // Executar o método listByName
        Page<ClientDTO> response = service.listByName(pageable, name);

        assertEquals(clients.size(), response.getTotalElements());
        assertEquals(clients.size(), response.getContent().size());
        assertEquals(client.getId(), response.getContent().get(0).getId());
        assertEquals(client.getName(), response.getContent().get(0).getName());
        assertEquals(client.getCpf(), response.getContent().get(0).getCpf());

        assertEquals(client2.getId(), response.getContent().get(1).getId());
        assertEquals(client2.getName(), response.getContent().get(1).getName());
        assertEquals(client2.getCpf(), response.getContent().get(1).getCpf());
        assertTrue(response.getContent().get(0).getName().toLowerCase().contains(name.toLowerCase()));
        assertTrue(response.getContent().get(1).getName().toLowerCase().contains(name.toLowerCase()));

    }

    @Test
    void findById_wouldShoudReturnClientById() {
        // Dados de entrada
        Long id = 1L;

        // Mock do repositório
        Client client = createClient(1L, "987654321", "Jane Smith", 3L, "32", "12345678",
                4L, "21", "92345678", 2L, "teste@gmail.com");

        when(repository.findById(id)).thenReturn(Optional.of(client));

        // Executar o método findById
        Optional<ClientDTO> response = service.findById(id);

        // Verificações
        assertTrue(response.isPresent());
        assertEquals(client.getId(), response.get().getId());
        assertEquals(client.getName(), response.get().getName());
        assertEquals(client.getCpf(), response.get().getCpf());
        assertEquals(client.getPhones().size(), response.get().getPhones().size());
        assertEquals(client.getEmails().size(), response.get().getEmails().size());
        assertEquals(client.getPhones().get(0).getDdd(), response.get().getPhones().get(0).getDdd());
        assertEquals(client.getPhones().get(0).getNumber(), response.get().getPhones().get(0).getNumber());
        assertEquals(client.getPhones().get(1).getDdd(), response.get().getPhones().get(1).getDdd());
        assertEquals(client.getPhones().get(1).getNumber(), response.get().getPhones().get(1).getNumber());
        assertEquals(client.getEmails().get(0).getEmail(), response.get().getEmails().get(0).getEmail());
    }

    @Test
    void findById_whoudReturnExceptionWhenClientNotFound() {
        // Dados de entrada
        Long id = 1L;

        // Mock do repositório
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Executar o método findById
        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> service.deleteById(id));
        assertTrue(runtimeException.getMessage().contains("Cliente não encontrado"));

    }

    @Test
    void deleteById_wouldShoudDeleteClientById() {
        // Dados de entrada
        Long id = 1L;

        // Mock do repositório
        Client client = createClient(1L, "987654321", "Jane Smith", 3L, "32", "12345678",
                4L, "21", "92345678", 2L, "teste@gmail.com");

        when(repository.findById(id)).thenReturn(Optional.of(client));
        doNothing().when(repository).deleteById(id);
    }

    @Test
    void deleteById_whoudReturnExceptionWhenClientNotFound() {
        // Dados de entrada
        Long id = 1L;

        // Mock do repositório
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Executar o método findById
        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> service.deleteById(id));
        assertTrue(runtimeException.getMessage().contains("Cliente não encontrado"));
    }

    @Test
    void updateById_ShouldReturnClientUpdated() {
        // Dados de entrada
        Long id = 1L;
        ClientDTO clientDTO = createClientDTO();

        // Mock do repositório
        Client newClient = createClient(1L, "123456789", "John Doe", 1L, "11", "12345678",
                2L, "21", "92345678", 1L, "teste@teste.com");

        Client oldClient = createClient(1L, "123456789", "John Doe", 1L, "11", "12345678",
                2L, "21", "92345678", 1L, "teste@teste.com");

        when(repository.findById(id)).thenReturn(Optional.of(oldClient));
        when(repository.save(newClient)).thenReturn(newClient);

        // Executar o método updateById
        Optional<ClientDTO> response = service.update(id, clientDTO);

        // Verificações
        assertTrue(response.isPresent());
        assertEquals(newClient.getId(), response.get().getId());
        assertEquals(newClient.getName(), response.get().getName());
        assertEquals(newClient.getCpf(), response.get().getCpf());
        assertEquals(newClient.getPhones().size(), response.get().getPhones().size());
        assertEquals(newClient.getEmails().size(), response.get().getEmails().size());
        assertEquals(newClient.getPhones().get(0).getDdd(), response.get().getPhones().get(0).getDdd());
        assertEquals(newClient.getPhones().get(0).getNumber(), response.get().getPhones().get(0).getNumber());
        assertEquals(newClient.getPhones().get(1).getDdd(), response.get().getPhones().get(1).getDdd());
        assertEquals(newClient.getPhones().get(1).getNumber(), response.get().getPhones().get(1).getNumber());
        assertEquals(newClient.getEmails().get(0).getEmail(), response.get().getEmails().get(0).getEmail());

    }


    private ClientDTO createClientDTO() {
        return ClientDTO.builder()
                .id(1L)
                .cpf("123456789")
                .name("John Doe")
                .phones(new ArrayList<>(List.of(
                        PhoneDTO.builder().id(1L)
                                .ddd("11")
                                .number("12345678").build(),
                        PhoneDTO.builder().id(2L)
                                .ddd("21")
                                .number("92345678").build()
                )))
                .emails(new ArrayList<>(List.of(
                        EmailDTO.builder()
                                .id(1L)
                                .email("teste@teste.com").build()
                )))
                .address(AddressDTO.builder()
                        .id(1L)
                        .street("Rua sucupira")
                        .number("123")
                        .complement("Casa")
                        .neighborhood("Jardim")
                        .city("São Paulo")
                        .state("SP")
                        .zipCode("12345678").build())
                .build();
    }

    private Client createClient(Long id, String cpf, String name, Long phoneId1, String ddd1, String number1,
                                Long phoneId2, String ddd2, String number2, Long emailId, String emailAddress) {
        Client client = new Client();

        client.setId(id);
        client.setName(name);
        client.setCpf(cpf);
        client.getPhones().add(new Phone(phoneId1, ddd1, number1, client));
        client.getPhones().add(new Phone(phoneId2, ddd2, number2, client));
        client.getEmails().add(new Email(emailId, emailAddress));
        client.setAddress(ADDRESS);
        client.getAddress().setClient(client);

        return client;
    }
}
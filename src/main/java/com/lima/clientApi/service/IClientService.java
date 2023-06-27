package com.lima.clientApi.service;

import com.lima.clientApi.model.dto.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IClientService {

    Optional<ClientDTO> save(ClientDTO clientDTO);

    Page<ClientDTO> listAll(Pageable page);

    Page<ClientDTO> listByAreaCode(Pageable page, String areaCode);

    Page<ClientDTO> listByName(Pageable page, String name);

    Optional<ClientDTO> findById(Long id);

    void deleteById(Long id);

    Optional<ClientDTO> update(Long id, ClientDTO request);
}

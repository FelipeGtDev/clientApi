package com.lima.clientApi.controller;

import com.lima.clientApi.exceptions.ResourceNotFoundException;
import com.lima.clientApi.model.dto.ClientDTO;
import com.lima.clientApi.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {

    private final IClientService service;

    @Autowired
    private ClientController(IClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ClientDTO request) {

        try {
            Optional<ClientDTO> response = service.save(request);
            log.info("Client saved: {}", response.orElse(null));
            return new ResponseEntity<>(response.orElse(null), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Erro ao salvar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao salvar cliente: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<Page<?>> listAll(
            @PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"id"}) Pageable page) {

        System.out.println(page);
        Page<ClientDTO> response = service.listAll(page);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/listByAreaCode/{areaCode}")
    public ResponseEntity<Page<?>> listByAreaCode(
            @PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"id"}) Pageable page,
            @PathVariable("areaCode") String areaCode) {

        Page<ClientDTO> response = service.listByAreaCode(page, areaCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<?>> listByName(
            @PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"id"}) Pageable page,
            @RequestParam String name) {

        Page<ClientDTO> response = service.listByName(page, name);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   @GetMapping("/{id}")
   public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            Optional<ClientDTO> response = service.findById(id);

            return new ResponseEntity<>(response.orElse(null), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            log.error("Erro ao buscar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        } catch (Exception e) {
            log.error("Erro ao buscar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ClientDTO request) {
        try {
            Optional<ClientDTO> response = service.update(id, request);
            log.info("Client updated: {}", response.orElse(null));
            return new ResponseEntity<>(response.orElse(null), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            log.error("Erro ao atualizar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar cliente: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        } catch (Exception e) {
            log.error("Erro ao atualizar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar cliente: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            service.deleteById(id);
            log.info("Client deleted: {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            log.error("Erro ao deletar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao deletar cliente: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        } catch (Exception e) {
            log.error("Erro ao deletar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao deletar cliente: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }
}

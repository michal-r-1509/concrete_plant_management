package com.concrete.concrete_plant_management.client.controller;

import com.concrete.concrete_plant_management.client.service.ClientService;
import com.concrete.concrete_plant_management.domain.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    ResponseEntity<Client> saveClient(@RequestBody @Valid Client toSave) {
        Client result = clientService.saveClient(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Client> patchClient(@PathVariable Long id, @RequestBody Client toUpdate) {
        Client result = clientService.updateClient(id, toUpdate);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Client> readClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<Client>> readAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping
    ResponseEntity<List<Client>> readAllClients(Sort sort) {
        return ResponseEntity.ok(clientService.getAllClients(sort));
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Client> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

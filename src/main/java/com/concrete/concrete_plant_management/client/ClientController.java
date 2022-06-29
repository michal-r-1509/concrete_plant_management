package com.concrete.concrete_plant_management.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    ResponseEntity<Client> saveClient(@RequestBody @Valid Client toSave){
        Client result = clientService.saveClient(toSave);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Client> readClient(@PathVariable int id){
        return ResponseEntity.ok(clientService.getClient(id));
    }

    @GetMapping
    ResponseEntity<List<Client>> readAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }
}

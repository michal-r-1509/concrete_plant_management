package com.concrete.concrete_plant_management.client;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clients")
class ClientController {

    private final ClientService clientService;

    public ClientController(final ClientService clientService) {
        this.clientService = clientService;

        //
        List<Client> list = new ArrayList<>();
        list.add(new Client("erbud sp. z o.o.", "różowa 63", "42160", "krakow", 1234567890L));
        list.add(new Client("januszex", "zielona 55", "42200", "czestochowa", 9876543210L));
        list.add(new Client("budimex SA", "ruda 46", "82812", "zabrze", 4456543212L));
        list.add(new Client("strabag", "kolorowa 665", "40885", "warszawa", 7776543213L));
        list.add(new Client("skanska", "tęczowa 115", "45411", "wrocław", 6585243218L));
        list.forEach(clientService::saveClient);
        //
    }

    @PostMapping
    ResponseEntity<Client> saveClient(@RequestBody @Valid Client toSave){
        Client result = clientService.saveClient(toSave);
        if (result == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Client> patchClient(@PathVariable int id, @RequestBody Client toUpdate){
        Client result = clientService.updateClient(id, toUpdate);
        if (result == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Client> readClient(@PathVariable int id){
        return ResponseEntity.ok(clientService.getClient(id));
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<Client>> readAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping
    ResponseEntity<List<Client>> readAllClients(Sort sort) {
        return ResponseEntity.ok(clientService.getAllClients(sort));
    }


    @DeleteMapping(value = "/{id}")
    ResponseEntity<Client> deleteClient(@PathVariable int id){
        boolean isDeleted = clientService.deleteClient(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}

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

        //
        Client a = new Client("michal", "chopina 63", "42160", "krakow", 1234567890L);
        Client b = new Client("angelika", "zielona 55", "42200", "czestochowa", 9876543210L);
        Client c = new Client("jurek", "ruda 46", "82812", "zabrze", 4456543212L);
        Client d = new Client("mietek and company", "kolorowa 665", "40885", "wawa", 7776543213L);
        Client e = new Client("arwekon", "bura 115", "45411", "krzepice", 6585243218L);
        clientService.saveClient(a);
        clientService.saveClient(b);
        clientService.saveClient(c);
        clientService.saveClient(d);
        clientService.saveClient(e);
        //
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

    @PutMapping(value = "/{id}")
    ResponseEntity<Client> patchClient(@PathVariable int id, @RequestBody Client toUpdate){
        Client result = clientService.updateClient(toUpdate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
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

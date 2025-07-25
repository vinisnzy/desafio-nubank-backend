package com.nubank.desafio_backend.controller;

import com.nubank.desafio_backend.dto.client.ClientRequest;
import com.nubank.desafio_backend.dto.client.ClientResponse;
import com.nubank.desafio_backend.dto.contact.ContactResponse;
import com.nubank.desafio_backend.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) {
        ClientResponse response = service.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clients = service.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}/contatos")
    public ResponseEntity<List<ContactResponse>> getContactsByClientId(@PathVariable Long id) {
        List<ContactResponse> contacts = service.getContactsByClientId(id);
        return ResponseEntity.ok(contacts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

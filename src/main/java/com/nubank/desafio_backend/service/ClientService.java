package com.nubank.desafio_backend.service;

import com.nubank.desafio_backend.dto.client.ClientRequest;
import com.nubank.desafio_backend.dto.client.ClientResponse;
import com.nubank.desafio_backend.dto.contact.ContactResponse;
import com.nubank.desafio_backend.exceptions.ClientNotFoundException;
import com.nubank.desafio_backend.mapper.ClientMapper;
import com.nubank.desafio_backend.mapper.ContactMapper;
import com.nubank.desafio_backend.model.Client;
import com.nubank.desafio_backend.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public ClientResponse createClient(ClientRequest request) {
        Client client = new Client();
        client.setName(request.name());
        client.setEmail(request.email());
        client.setContacts(List.of());

        client = repository.save(client);
        return ClientMapper.toResponse(client);
    }

    public List<ClientResponse> getAllClients() {
        List<Client> clients = repository.findAll();
        return clients.stream()
                .map(ClientMapper::toResponse)
                .toList();
    }

    public Client getClientById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
    }

    public List<ContactResponse> getContactsByClientId(Long clientId) {
        Client client = getClientById(clientId);
        return client.getContacts().stream()
                .map(ContactMapper::toResponse)
                .toList();
    }

    public void deleteClient(Long id) {
        repository.deleteById(id);
    }
}

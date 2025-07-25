package com.nubank.desafio_backend.mapper;

import com.nubank.desafio_backend.dto.client.ClientRequest;
import com.nubank.desafio_backend.dto.client.ClientResponse;
import com.nubank.desafio_backend.dto.contact.ContactResponse;
import com.nubank.desafio_backend.model.Client;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientMapper {

    private ClientMapper(){
        // Private constructor to prevent instantiation
    }

    public static Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setName(request.name());
        client.setEmail(request.email());
        client.setContacts(List.of());
        return client;
    }

    public static ClientResponse toResponse(Client client) {
        List<ContactResponse> contacts = client.getContacts().stream()
                .map(ContactMapper::toResponse)
                .toList();
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                contacts
        );
    }
}

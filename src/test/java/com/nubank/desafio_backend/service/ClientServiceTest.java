package com.nubank.desafio_backend.service;

import com.nubank.desafio_backend.dto.client.ClientRequest;
import com.nubank.desafio_backend.dto.client.ClientResponse;
import com.nubank.desafio_backend.dto.contact.ContactResponse;
import com.nubank.desafio_backend.exceptions.ClientNotFoundException;
import com.nubank.desafio_backend.model.Client;
import com.nubank.desafio_backend.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService service;

    @Mock
    private ClientRepository repository;

    private ClientRequest request;
    private ClientResponse response;
    private Client client;
    private Long id;

    @BeforeEach
    void setUp() {
        id = 1L;
        client = new Client(
                id,
                "nome do cliente",
                "cliente@gmail,com",
                List.of()
        );
        request = new ClientRequest(
                client.getName(),
                client.getEmail()
        );
        response = new ClientResponse(
                id,
                client.getName(),
                client.getEmail(),
                List.of()
        );
    }

    @Test
    void shouldCreateClient() {
        when(repository.save(any(Client.class))).thenReturn(client);

        ClientResponse result = service.createClient(request);

        assertNotNull(result);
        assertEquals(result.id(), client.getId());
        assertEquals(result.name(), client.getName());
        assertEquals(result.email(), client.getEmail());
        assertTrue(result.contacts().isEmpty());
    }

    @Test
    void shouldGetAllClients() {
        when(repository.findAll()).thenReturn(List.of(client));

        List<ClientResponse> result = service.getAllClients();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(response, result.getFirst());
    }

    @Test
    void shouldGetClientById() {
        when(repository.findById(id)).thenReturn(Optional.of(client));

        Client result = service.getClientById(id);

        assertNotNull(result);
        assertEquals(client.getId(), result.getId());
        assertEquals(client.getName(), result.getName());
        assertEquals(client.getEmail(), result.getEmail());
    }

    @Test
    void shouldThrowsClientNotFoundExceptionWhenClientNotFoundById() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class, () -> {
            service.getClientById(id);
        });

        assertNotNull(exception);
        assertEquals(ClientNotFoundException.class, exception.getClass());
        assertEquals("Client not found with id: " + id, exception.getMessage());
    }

    @Test
    void shouldGetContactsByClientId() {
        when(repository.findById(id)).thenReturn(Optional.of(client));

        List<ContactResponse> result = service.getContactsByClientId(id);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldDeleteClient() {
        service.deleteClient(id);

        verify(repository).deleteById(id);
    }
}
package com.nubank.desafio_backend.service;

import com.nubank.desafio_backend.dto.contact.ContactRequest;
import com.nubank.desafio_backend.dto.contact.ContactResponse;
import com.nubank.desafio_backend.model.Client;
import com.nubank.desafio_backend.model.Contact;
import com.nubank.desafio_backend.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @InjectMocks
    private ContactService contactService;

    @Mock
    private ClientService clientService;

    @Mock
    private ContactRepository repository;

    @Test
    void shouldCreateContact() {
        Long id = 1L;
        Contact contact = new Contact(id, "nome do contato", "contato@gmail.com", new Client());
        ContactRequest request = new ContactRequest(contact.getName(), contact.getEmail(), id);

        when(clientService.getClientById(id)).thenReturn(contact.getClient());
        when(repository.save(any(Contact.class))).thenReturn(contact);

        ContactResponse result = contactService.createContact(request);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(request.name(), result.name());
        assertEquals(request.email(), result.email());
    }
}
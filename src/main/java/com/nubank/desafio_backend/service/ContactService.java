package com.nubank.desafio_backend.service;

import com.nubank.desafio_backend.dto.contact.ContactRequest;
import com.nubank.desafio_backend.dto.contact.ContactResponse;
import com.nubank.desafio_backend.mapper.ContactMapper;
import com.nubank.desafio_backend.model.Contact;
import com.nubank.desafio_backend.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactRepository repository;

    private final ClientService clientService;

    public ContactService(ContactRepository repository, ClientService clientService) {
        this.repository = repository;
        this.clientService = clientService;
    }

    public ContactResponse createContact(ContactRequest request) {
        Contact contact = ContactMapper.toEntity(request);
        contact.setClient(clientService.getClientById(request.clientId()));
        contact = repository.save(contact);
        return ContactMapper.toResponse(contact);
    }
}

package com.nubank.desafio_backend.controller;

import com.nubank.desafio_backend.dto.contact.ContactRequest;
import com.nubank.desafio_backend.dto.contact.ContactResponse;
import com.nubank.desafio_backend.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contatos")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(@Valid @RequestBody ContactRequest request) {
        ContactResponse response = service.createContact(request);
        return ResponseEntity.ok(response);
    }
}

package com.nubank.desafio_backend.dto.client;

import com.nubank.desafio_backend.dto.contact.ContactResponse;

import java.util.List;

public record ClientResponse(
        Long id,
        String name,
        String email,
        List<ContactResponse> contacts
) {
}

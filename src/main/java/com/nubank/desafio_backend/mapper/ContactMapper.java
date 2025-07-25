package com.nubank.desafio_backend.mapper;

import com.nubank.desafio_backend.dto.contact.ContactRequest;
import com.nubank.desafio_backend.dto.contact.ContactResponse;
import com.nubank.desafio_backend.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    private ContactMapper() {
        // Private constructor to prevent instantiation
    }

    public static Contact toEntity(ContactRequest request) {
        Contact contact = new Contact();
        contact.setName(request.name());
        contact.setEmail(request.email());
        return contact;
    }

    public static ContactResponse toResponse(Contact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getName(),
                contact.getEmail()
        );
    }
}
